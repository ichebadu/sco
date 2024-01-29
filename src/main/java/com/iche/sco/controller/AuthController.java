package com.iche.sco.controller;


import com.iche.sco.dto.user.request.LoginRequest;
import com.iche.sco.dto.user.request.OtpVerificationRequest;
import com.iche.sco.dto.user.request.RegistrationRequest;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.dto.user.response.LoginResponse;
import com.iche.sco.dto.user.response.RegistrationResponse;
import com.iche.sco.service.tokenService.TokenService;
import com.iche.sco.service.userService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.iche.sco.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_BASE_URL)
@Slf4j
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;



    @Operation(
            summary = "User Registration REST API",
            description = "User Registration REST API is used to register a new user"
    )
    @ApiResponse(responseCode = "200", description = "Http status 200 SUCCESS")
    @PostMapping(REGISTRATION)
    public ResponseEntity<APIResponse<RegistrationResponse>> registerAnyUser(@Valid
            @RequestBody RegistrationRequest registrationRequest
    ){
        return new ResponseEntity<>(userService.registerUser(registrationRequest), HttpStatus.CREATED);
    }
    @PostMapping(VERIFICATION)
    @Operation(
            summary = "User Verification REST API",
            description = "User Verification REST API is used to verify user's email using OTP"
    )
    @ApiResponse(responseCode = "200", description = "Http status 200 SUCCESS")
    public ResponseEntity<APIResponse<String>> verifyUser(
            @RequestBody @Valid OtpVerificationRequest otpVerificationRequest
    ){
        return new ResponseEntity<>(tokenService.verifyUserOtp(otpVerificationRequest),HttpStatus.OK);
    }
    @PostMapping(LOGIN)
    @Operation(
            summary = "User Login REST API",
            description = "User Login REST API is used for user authentication"
    )
    @ApiResponse(responseCode = "200", description = "Http status 200 SUCCESS")
    public ResponseEntity<APIResponse<LoginResponse>>login(@Valid
            @RequestBody LoginRequest loginRequest
    ){
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

}
