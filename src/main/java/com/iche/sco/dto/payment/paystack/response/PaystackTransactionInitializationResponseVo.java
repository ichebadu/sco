package com.iche.sco.dto.payment.paystack.response;

import com.iche.sco.dto.payment.paystack.dto.Payload;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class PaystackTransactionInitializationResponseVo {
    private String status;
    private String message;
    private Payload data;
}
