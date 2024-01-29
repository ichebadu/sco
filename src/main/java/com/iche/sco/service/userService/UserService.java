package com.iche.sco.service.userService;


import com.iche.sco.dto.user.request.LoginRequest;
import com.iche.sco.dto.user.request.RegistrationRequest;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.dto.user.response.LoginResponse;
import com.iche.sco.dto.user.response.RegistrationResponse;
import com.iche.sco.model.Users;

public interface UserService {
    APIResponse<RegistrationResponse> registerUser(RegistrationRequest registrationRequest);

    APIResponse<LoginResponse> login(LoginRequest loginRequest);

    Users getUserByEmail(String email);
}
