package com.iche.sco.service.tokenService;



import com.iche.sco.dto.user.request.OtpVerificationRequest;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.enums.ResponseCode;
import com.iche.sco.exception.InvalidCredentialsException;
import com.iche.sco.exception.OtpException;
import com.iche.sco.exception.UserNotFoundException;
import com.iche.sco.model.*;
import com.iche.sco.registrationEvent.UserRegistrationEvent;
import com.iche.sco.respository.TokenRepository;
import com.iche.sco.utils.RandomGeneratedValue;
import com.iche.sco.utils.UserVerification;
import com.iche.sco.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl<T extends BaseUser> implements TokenService<T>{
    private final TokenRepository confirmationTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserVerification userVerification;

    private final Validations<OtpVerificationRequest> otpVerificationRequestValidations;

    @Override
    public APIResponse<String> verifyUserOtp(OtpVerificationRequest otpVerificationRequest) {
        otpVerificationRequestValidations.validate(otpVerificationRequest);
        BaseUser user = userVerification.verifyUserByEmail(otpVerificationRequest.getEmail());

        log.info("Verifying OTP: " + user.getEmail());
        Token confirmationTokenConfirmation = confirmationTokenRepository.findByUser_EmailAndOtp(user.getEmail(), otpVerificationRequest.getOtp());
        System.out.println(confirmationTokenConfirmation);

        if (confirmationTokenConfirmation == null || isOtpExpired(confirmationTokenConfirmation)) {
            throw new InvalidCredentialsException("invalid or Expired credential");
        }
        user.setStatus(true);
        userVerification.saveUser(user);
        log.info(confirmationTokenConfirmation.getUser().toString());

        return new APIResponse<>(ResponseCode.VERIFICATION_SUCCESS_RESPONSE.getMessage(), ResponseCode.VERIFICATION_SUCCESS_RESPONSE.getStatusCode(),"account verified");

    }
    @Override
    public void sendOtp(BaseUser user, String otp, Token newConfirmationToken){
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
        Date otpCreatedAt = confirmationToken.getExpiresAt();
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Convert Date to LocalDateTime
        LocalDateTime otpCreatedLocalDateTime = LocalDateTime.ofInstant(otpCreatedAt.toInstant(), ZoneId.systemDefault());

        // Calculate the duration between OTP creation and current time
        Duration duration = Duration.between(otpCreatedLocalDateTime, currentDateTime);

        long minutesPassed = duration.toMinutes();
        long otpExpiresAt = 4; // Assuming OTP expires in 4 minutes

        return minutesPassed > otpExpiresAt;
    }
    @Override
    public void saveOtp(Token confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
    @Override
    public Token generateOtp(BaseUser user) {
        String otp = RandomGeneratedValue.generateRandomValues();
        Token token;
        if(user instanceof Admin admin){
            token  = new Token(otp, admin);
        } else if(user instanceof Merchant merchant){
            token  = new Token(otp, merchant);
        }
        else if(user instanceof AppUser appUser){
            token  = new Token(otp, appUser);
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
        return token;
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
