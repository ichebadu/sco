package com.iche.sco.dto.user.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {

    @Column(name="email", nullable = false)
    @NotBlank(message = "Enter your email")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    private String email;

    @Column(name="password", nullable = false)
    @NotBlank(message = "Enter your password")
    @Pattern(regexp = "^[^?*!\\\\/$%^()~<>?\":}{\\[\\]|+=_\\-&#@.,;]+$", message = "Invalid password format")
    private String password;

}
