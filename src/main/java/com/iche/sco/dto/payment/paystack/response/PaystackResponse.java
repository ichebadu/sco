package com.iche.sco.dto.payment.paystack.response;

import com.iche.sco.dto.payment.paystack.dto.BankData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaystackResponse {
    private String message;
    private String status;
    private List<BankData> data;
}
