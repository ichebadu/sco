package com.iche.sco.dto.user.response;

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
