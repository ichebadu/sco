package com.iche.sco.service.userService;


import com.iche.sco.dto.user.request.GlobalRegistrationRequest;
import com.iche.sco.dto.user.request.LoginRequest;
import com.iche.sco.dto.user.request.AppUserRegistrationRequest;
import com.iche.sco.dto.user.request.MerchantRegistrationRequest;
import com.iche.sco.enums.ResponseCode;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.dto.user.response.LoginResponse;
import com.iche.sco.dto.user.response.RegistrationResponse;
import com.iche.sco.exception.*;
import com.iche.sco.model.*;
import com.iche.sco.registrationEvent.UserRegistrationEvent;
import com.iche.sco.respository.AdminRepository;
import com.iche.sco.respository.AppUserRepository;
import com.iche.sco.respository.MerchantRepository;
import com.iche.sco.respository.RoleRepository;
import com.iche.sco.security.JwtService;
import com.iche.sco.service.tokenService.TokenServiceImpl;
import com.iche.sco.utils.MapUtils;
import com.iche.sco.utils.UserVerification;
import com.iche.sco.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final TokenServiceImpl<Merchant> tokenServiceMerchant;
    private final TokenServiceImpl<Admin> tokenServiceAdmin;
    private final TokenServiceImpl<AppUser> tokenServiceAppUser;
    private final ApplicationEventPublisher publisher;
    private final JwtService jwtService;
    private final Validations<AppUserRegistrationRequest> appRegistrationRequestValidationUtils;
    private final Validations<MerchantRegistrationRequest> merchantRegistrationRequestValidationUtils;
    private final Validations<LoginRequest> loginRequestValidations;
    private final UserVerification userVerification;

    private final MapUtils mapUtils;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public APIResponse<RegistrationResponse> registerUser(GlobalRegistrationRequest registrationRequest) {
        AppUserRegistrationRequest appUserRegistrationRequest = new AppUserRegistrationRequest(registrationRequest);
        MerchantRegistrationRequest merchantRegistrationRequest = new MerchantRegistrationRequest(registrationRequest);
        appRegistrationRequestValidationUtils.validate(appUserRegistrationRequest);
        merchantRegistrationRequestValidationUtils.validate(merchantRegistrationRequest);

        userVerification.checkUserExists(registrationRequest.getEmail());
        Token token;

        String formattedPhoneNumber = savePhoneNumber(registrationRequest.getPhoneNumber());

        if (userVerification.isPhoneNumberRegistered(registrationRequest.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Phone number is already registered.");
        }

        if (registrationRequest.getRole().equals("ROLE_APPUSER")) {
            AppUser appUser = mapUtils.mapRequestToAppUser(appUserRegistrationRequest, formattedPhoneNumber);
            appUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            appUser.setRoles(validateRoleExists(appUserRegistrationRequest.getRole()));
            AppUser savedAppUser = appUserRepository.save(appUser);
            token = tokenServiceAppUser.generateOtp(savedAppUser);
            token.setUser(savedAppUser);
            tokenServiceMerchant.saveOtp(token);
            String otp = token.getOtp();
            log.info(otp);
            try {
                publisher.publishEvent(new UserRegistrationEvent(savedAppUser, otp));
            } catch (RuntimeException e) {
                throw new OtpException("COULD NOT SEND MAIL");
            }

            RegistrationResponse registrationResponse = RegistrationResponse.builder()
                    .username(savedAppUser.getUsername())
                    .message("Registration Successful")
                    .build();
            return new APIResponse<>(ResponseCode.REGISTRATION_SUCCESS_RESPONSE.getMessage(), ResponseCode.REGISTRATION_SUCCESS_RESPONSE.getStatusCode(), registrationResponse);
        } else if (registrationRequest.getRole().equals("ROLE_MERCHANT")) {
            Merchant merchant = mapUtils.mapRequestToMerchant(merchantRegistrationRequest, formattedPhoneNumber);
            merchant.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            merchant.setRoles(validateRoleExists(merchantRegistrationRequest.getRole()));
            Merchant savedMerchant = merchantRepository.save(merchant);
            token = tokenServiceMerchant.generateOtp(savedMerchant);
            token.setUser(savedMerchant);
            tokenServiceMerchant.saveOtp(token);
            String otp = token.getOtp();

            log.info(otp);
            try {
                publisher.publishEvent(new UserRegistrationEvent(savedMerchant, otp));
            } catch (RuntimeException e) {
                throw new OtpException("COULD NOT SEND MAIL");
            }

            RegistrationResponse registrationResponse = RegistrationResponse.builder()
                    .username(savedMerchant.getUsername())
                    .message("Registration Successful")
                    .build();
            return new APIResponse<>(ResponseCode.REGISTRATION_SUCCESS_RESPONSE.getMessage(), ResponseCode.REGISTRATION_SUCCESS_RESPONSE.getStatusCode(), registrationResponse);
        } else {
            throw new InvalidCredentialsException("Invalid role");
        }
    }



    public Role validateRoleExists(String registrationRequest){
        return roleRepository.findByRoleName(registrationRequest)
                .orElseThrow( () -> new InvalidCredentialsException("Role does not exist"));
    }

    @Override
    public APIResponse<LoginResponse> login(LoginRequest loginRequest) {
        loginRequestValidations.validate(loginRequest);
        BaseUser user = userVerification.verifyUserByEmail(loginRequest.getEmail());
        if(user.getStatus().equals(false)){
            throw new UserDisabledException("user is not verify, please check your email and verify your account or click on resend otp");
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

    private String savePhoneNumber(String phoneNumber) {
        StringBuilder formatNumber = new StringBuilder();

        for (char digit : phoneNumber.toCharArray()) {
            if (Character.isDigit(digit)) {
                formatNumber.append(digit);
            }
        }

        if (formatNumber.length() == 11) {
            char firstDigit = formatNumber.charAt(0);

            if (firstDigit == '0') {
                String formattedPhoneNumber = "+234" + formatNumber;

                if (userVerification.isPhoneNumberRegistered(formattedPhoneNumber)) {
                    throw new UserAlreadyExistsException("Phone number is already registered.");
                }
                return formattedPhoneNumber;
            } else {
                throw new InvalidCredentialsException("Invalid phone number format. Must start with zero (0).");
            }
        } else {
            throw new InvalidCredentialsException("Invalid phone number format. Must be 11 digits.");
        }
    }

}
