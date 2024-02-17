package com.iche.sco.model;


import com.iche.sco.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppUser extends BaseUser{
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
