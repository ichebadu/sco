package com.iche.sco.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PaystackUtils {
    @Autowired
    private Environment environment;
    @Value("${paystack_secret_key}")
    public  String paystackSecretKey;
    @Value("${paystack-public-key}")
    private  String payStackPublicKey;
    private static final  String PAY_STACK_URL = "https://api.paystack.co/bank";
    private static final String INITIALISE_TRANSACTION_URL  = "https://api.paystack.co/transaction/initialize";

    public static String initializeTransaction(){
        return INITIALISE_TRANSACTION_URL;
    }
    @Bean
    public String getPaystackSecretKey(){
      return paystackSecretKey;
    }
    @Bean
    public String getPaystackPublicKey(){
        return payStackPublicKey;
    }
    @Bean
    public static String paystackUrl(){
       return PAY_STACK_URL;
    }
}
