package com.iche.sco.service.paymentService;

import com.iche.sco.dto.payment.paystack.request.TransactionRequest;
import com.iche.sco.dto.payment.paystack.response.PaystackTransactionInitializationResponseVo;
import com.iche.sco.dto.globalResponse.APIResponse;

public interface PaymentService {
    APIResponse<PaystackTransactionInitializationResponseVo> initialisePayment(TransactionRequest transactionRequest);

    APIResponse<String> verifyPayment(String trxref, String reference);
}

