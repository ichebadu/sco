package com.iche.sco.dto.response.userServiceResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class RegistrationResponse {
    private String username;
    private String message;
}
