package com.iche.sco.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProfileRequestAppUser {
    private Date dateOfBirth;
    private String phone;
    private String gender;
}
