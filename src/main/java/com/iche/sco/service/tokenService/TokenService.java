package com.iche.sco.service.tokenService;


import com.iche.sco.dto.user.request.OtpVerificationRequest;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.model.Token;
import com.iche.sco.model.Users;

public interface TokenService {
    APIResponse<String> verifyUserOtp(OtpVerificationRequest otpVerificationRequest);

    void sendOtp(Users user, String otp, Token newConfirmationToken);

    boolean isOtpExpired(Token confirmationToken);

    void saveOtp(Token confirmationToken);

    Token generateOtp(Users user);

    String verifyToken(String token);
}
