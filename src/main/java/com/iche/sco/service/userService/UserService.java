package com.iche.sco.service.userService;


import com.iche.sco.dto.user.request.GlobalRegistrationRequest;
import com.iche.sco.dto.user.request.LoginRequest;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.dto.user.response.LoginResponse;
import com.iche.sco.dto.user.response.RegistrationResponse;


public interface UserService {
    APIResponse<?> registerUser(GlobalRegistrationRequest registrationRequest);

    APIResponse<LoginResponse> login(LoginRequest loginRequest);
}
