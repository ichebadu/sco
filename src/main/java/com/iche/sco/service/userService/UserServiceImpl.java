package com.iche.sco.service.userService;


import com.iche.sco.dto.user.request.LoginRequest;
import com.iche.sco.dto.user.request.RegistrationRequest;
import com.iche.sco.enums.ResponseCode;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.dto.user.response.LoginResponse;
import com.iche.sco.dto.user.response.RegistrationResponse;
import com.iche.sco.enums.Role;
import com.iche.sco.exception.*;
import com.iche.sco.model.Token;
import com.iche.sco.model.Users;
import com.iche.sco.registrationEvent.UserRegistrationEvent;
import com.iche.sco.respository.UserRepository;
import com.iche.sco.security.JwtService;
import com.iche.sco.service.tokenService.TokenServiceImpl;
import com.iche.sco.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenServiceImpl tokenServiceImpl;
    private final ApplicationEventPublisher publisher;
    private final JwtService jwtService;
    private final Validations<RegistrationRequest> registrationRequestValidationUtils;
    private final Validations<LoginRequest> loginRequestValidations;

    @Override
    public APIResponse<RegistrationResponse> registerUser(RegistrationRequest registrationRequest) {
        registrationRequestValidationUtils.validate(registrationRequest);
        validateUserExistence(registrationRequest.getEmail());
        System.out.println(registrationRequest.getPassword());
   //     String formattedPhoneNumber = savePhoneNumber(registrationRequest.getPhoneNumber());

                if (isPhoneNumberRegistered(registrationRequest.getPhoneNumber())) {
                    throw new UserAlreadyExistsException("Phone number is already registered.");
                }
        Users user = Users.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getFirstName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .createDate(LocalDateTime.now())
    //            .phoneNumber(formattedPhoneNumber)
                .role(registrationRequest.getRole())
                .status(false)
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();

        var saveUser = userRepository.save(user);

        Token token = tokenServiceImpl.generateOtp(saveUser);
        token.setUser(user);
        tokenServiceImpl.saveOtp(token);
        String otp = token.getOtp();
        log.info(otp);
        try {
            publisher.publishEvent(new UserRegistrationEvent(user, otp));
        } catch (RuntimeException e){
            throw new OtpException("COULD NOT SEND MAIL");
        }

        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .username(user.getUsername())
                .message("Registration Successful")
                .build();
        return new APIResponse<>(ResponseCode.REGISTRATION_SUCCESS_RESPONSE.getMessage(),ResponseCode.REGISTRATION_SUCCESS_RESPONSE.getStatusCode(), registrationResponse);
    }

    @Override
    public APIResponse<LoginResponse> login(LoginRequest loginRequest) {
        loginRequestValidations.validate(loginRequest);
        Users user = getUserByEmail(loginRequest.getEmail());
        if(user.getStatus().equals(false)){
            throw new UserDisabledException("Password do not match");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new SamePasswordException("Invalid Password");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),user.getPassword()
        );

        String jwtAccessToken = jwtService.generateToken(user);
        String jwtRefreshToken = jwtService.generateRefreshToken(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponse loginResponse = LoginResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
        return new APIResponse<>(ResponseCode.LOGIN_SUCCESS_RESPONSE.getMessage(),ResponseCode.LOGIN_SUCCESS_RESPONSE.getStatusCode(), loginResponse);
    }
    public void validateUserExistence(String email){
        if(userRepository.existsByEmail(email)){
            throw new UserAlreadyExistsException(email);
        }
    }
    @Override
    public Users getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
    }
//    private String savePhoneNumber(String phoneNumber) {
//        StringBuilder formatNumber = new StringBuilder();
//
//        for (char digit : phoneNumber.toCharArray()) {
//            if (Character.isDigit(digit)) {
//                formatNumber.append(digit);
//            }
//        }
//
//        if (formatNumber.length() == 11) {
//            char firstDigit = formatNumber.charAt(0);
//
//            if (firstDigit == '0') {
//                String formattedPhoneNumber = "+234" + formatNumber;
//
//                if (isPhoneNumberRegistered(formattedPhoneNumber)) {
//                    throw new UserAlreadyExistsException("Phone number is already registered.");
//                }
//                return formattedPhoneNumber;
//            } else {
//                throw new InvalidCredentialsException("Invalid phone number format. Must start with zero (0).");
//            }
//        } else {
//            throw new InvalidCredentialsException("Invalid phone number format. Must be 11 digits.");
//        }
//    }

    private boolean isPhoneNumberRegistered(String formattedPhoneNumber) {
        return userRepository.existsByPhoneNumber(formattedPhoneNumber);
    }

}
