package com.iche.sco.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iche.sco.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

//import java.time.LocalDate;
import java.time.Instant;
//import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "token_tbl")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
//    @Column(name = "create_date", nullable = false )
//    private LocalDateTime expiresAt;

    @Column(name = "create_date", nullable = false )
    private Date expiresAt;
    @Column(name = "otp",unique = true)
    private String otp;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id"
    )
    private BaseUser user;
    public Token(String otp, BaseUser user){
        this.otp = otp;
        this.user =user;
        this.expiresAt = Date.from(expiresAt().atZone(ZoneId.systemDefault()).toInstant());
    }
    public LocalDateTime expiresAt(){
        return LocalDateTime.now().plusMinutes(5);
    }

}
