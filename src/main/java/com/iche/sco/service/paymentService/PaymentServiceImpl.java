package com.iche.sco.service.paymentService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iche.sco.dto.payment.paystack.request.TransactionRequest;
import com.iche.sco.dto.payment.paystack.response.PaystackTransactionInitializationResponseVo;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.enums.ResponseCode;
import com.iche.sco.respository.AccountRepository;
import com.iche.sco.respository.BankRepository;
import com.iche.sco.utils.PaystackUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import static com.iche.sco.utils.PaystackUtils.paystackSecretKey;
import static org.yaml.snakeyaml.tokens.Token.ID.Value;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaystackUtils paystackUtils;
    private final RestTemplate restTemplate;
    private final BankRepository bankRepository;
//    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final HttpHeaders headers;

    @Override
    public APIResponse<PaystackTransactionInitializationResponseVo> initialisePayment(TransactionRequest transactionRequest){
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setCacheControl("no-cache");
        headers.setBearerAuth(paystackUtils.getPaystackSecretKey());
        int price = Integer.parseInt(transactionRequest.getAmount())*100;
        transactionRequest.setAmount(String.valueOf(price));

        HttpEntity<TransactionRequest> requestEntity = new HttpEntity<>(transactionRequest, headers);

        ResponseEntity<PaystackTransactionInitializationResponseVo> responseEntity = restTemplate.postForEntity(paystackUtils.initializeTransaction(), requestEntity, PaystackTransactionInitializationResponseVo.class);
        return new APIResponse<>(ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getMessage(),ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getStatusCode(), responseEntity.getBody());
    }

    @Override
    public APIResponse<String> verifyPayment(String trxref, String reference) {
        String url = "https://api.paystack.co/transaction/verify/"+reference;
        HttpHeaders headers = new HttpHeaders();
        System.out.println(paystackUtils.getPaystackSecretKey());
        headers.setBearerAuth(paystackUtils.getPaystackSecretKey());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class,
                reference
        );
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseBody = objectMapper.readTree(responseEntity.getBody());
                System.out.println(responseBody);
                if(responseBody.has("data")) {
                    JsonNode data = responseBody.get("data");
                    System.out.println(data);

                    String statusValue = data.get("status").asText();
                    System.out.println(statusValue);
                    if (statusValue.equals("success")) {
                        int amount = data.get("amount").asInt();
                        String amounts = String.valueOf(amount);
                        System.out.println(amount);

                    }
                    return new APIResponse<>(ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getMessage(), ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getStatusCode(), statusValue);
                }else{
                    return new APIResponse<>(ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getMessage(), ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getStatusCode(), "statusValue");
                }

            } catch (Exception e) {
                // Handle JSON parsing exceptions
                e.printStackTrace();
                return new APIResponse<>(ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getMessage(), ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getStatusCode(), e.getMessage());

            }

        }
        return new APIResponse<>(ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getMessage(),ResponseCode.TRANSACTION_INITIALIZE_SUCCESS.getStatusCode(), responseEntity.getBody());

    }


}
