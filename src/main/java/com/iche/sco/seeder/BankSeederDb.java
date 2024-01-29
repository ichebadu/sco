package com.iche.sco.seeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iche.sco.dto.payment.paystack.dto.BankData;
import com.iche.sco.dto.payment.paystack.response.PaystackResponse;
import com.iche.sco.model.Bank;
import com.iche.sco.respository.BankRepository;
import com.iche.sco.utils.PaystackUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BankSeederDb implements CommandLineRunner {
    private final HttpHeaders httpHeaders;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final BankRepository bankRepository;
    private final PaystackUtils paystackUtils;
    @Override
    public void run(String... args) throws Exception {
        httpHeaders.setCacheControl("no-cache");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<?> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, URI.create(PaystackUtils.paystackUrl()));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        try{
            PaystackResponse paystackResponse = objectMapper.readValue( responseEntity.getBody(), PaystackResponse.class);
            List<BankData> bankDataList = paystackResponse.getData();
            bankRepository.deleteAll();

            List<Bank> banks = bankDataList.stream()
                    .map(bankData -> Bank.builder()
                            .bankCode(bankData.getCode())
                            .bankName(bankData.getName())
                            .build())
                    .collect(Collectors.toList());

            bankRepository.saveAll(banks);

        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
