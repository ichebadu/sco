package com.iche.sco.controller;

import com.iche.sco.dto.payment.paystack.request.TransactionRequest;
import com.iche.sco.dto.payment.paystack.response.PaystackTransactionInitializationResponseVo;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.service.paymentService.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initialize-payment")
    public ResponseEntity<APIResponse<PaystackTransactionInitializationResponseVo>> initializePayment(@RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(paymentService.initialisePayment(transactionRequest), HttpStatus.OK);
    }
    @GetMapping("/callback")
    public ResponseEntity<APIResponse<String>> callBackUrl(@RequestParam ("trxref") String trxref, @RequestParam ("reference") String reference){
        return new ResponseEntity<>(paymentService.verifyPayment(trxref,reference), HttpStatus.OK);
    }
}
