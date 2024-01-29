package com.iche.sco.dto.response;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class  PaystackResponseVo {
    private String status;
    private String message;

    private InnerData data;
}
