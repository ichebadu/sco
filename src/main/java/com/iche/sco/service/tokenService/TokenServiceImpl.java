package com.iche.sco.service.tokenService;



import com.iche.sco.dto.user.request.OtpVerificationRequest;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.enums.ResponseCode;
import com.iche.sco.exception.InvalidCredentialsException;
import com.iche.sco.exception.OtpException;
import com.iche.sco.exception.UserNotFoundException;
import com.iche.sco.model.Token;
import com.iche.sco.model.Users;
import com.iche.sco.registrationEvent.UserRegistrationEvent;
import com.iche.sco.respository.TokenRepository;
import com.iche.sco.respository.UserRepository;
import com.iche.sco.utils.RandomGeneratedValue;
import com.iche.sco.utils.UserVerification;
import com.iche.sco.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService{
    private final TokenRepository confirmationTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserVerification userVerification;
    private final UserRepository userRepository;

    private final Validations<OtpVerificationRequest> otpVerificationRequestValidations;

    @Override
    public APIResponse<String> verifyUserOtp(OtpVerificationRequest otpVerificationRequest) {
        otpVerificationRequestValidations.validate(otpVerificationRequest);
        Users user = userVerification.verifyUserByEmail(otpVerificationRequest.getEmail());

        log.info("Verifying OTP: " + user.getEmail());
        Token confirmationTokenConfirmation = confirmationTokenRepository.findByUser_EmailAndOtp(user.getEmail(), otpVerificationRequest.getOtp());
        System.out.println(confirmationTokenConfirmation);

        if (confirmationTokenConfirmation == null && isOtpExpired(confirmationTokenConfirmation)) {
            throw new InvalidCredentialsException("invalid or Expired credential");

        }
        log.info(confirmationTokenConfirmation.getUser().toString());
        user.setStatus(true);
        userRepository.save(user);
        return new APIResponse<>(ResponseCode.VERIFICATION_SUCCESS_RESPONSE.getMessage(), ResponseCode.VERIFICATION_SUCCESS_RESPONSE.getStatusCode(),"account verified");

    }
    @Override
    public void sendOtp(Users user, String otp, Token newConfirmationToken){
        Token foundConfirmationToken = confirmationTokenRepository.findByUserId(user.getId());

        if(foundConfirmationToken != null){
            confirmationTokenRepository.delete(foundConfirmationToken);
        }
        confirmationTokenRepository.save(newConfirmationToken);
        log.info(otp);
        applicationEventPublisher.publishEvent(new UserRegistrationEvent(user,otp));
    }

    @Override
    public boolean isOtpExpired(Token confirmationToken){
        LocalDateTime otpCreatedAt = confirmationToken.getExpiresAt();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(otpCreatedAt, currentDateTime);
        long minutesPassed = duration.toMinutes();
        long otpExpiresAt = 4;
        return minutesPassed > otpExpiresAt;
    }
    @Override
    public void saveOtp(Token confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
    @Override
    public Token generateOtp(Users user) {
        String otp = RandomGeneratedValue.generateRandomValues();
        return new Token(otp, user);
    }
    @Override
    public String verifyToken(String token){
        Token confirmationToken = confirmationTokenRepository.findByOtp(token)
                .orElseThrow(()-> new UserNotFoundException("Invalid Credential"));
        if(isOtpExpired(confirmationToken)){
            confirmationTokenRepository.delete(confirmationToken);
            throw new OtpException("OTP is Expired");
        }
        return confirmationToken.getUser().getEmail();
    }
}
