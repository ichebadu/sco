package com.iche.sco.applicationConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iche.sco.model.AppUser;
import com.iche.sco.model.Merchant;
import com.iche.sco.respository.AdminRepository;
import com.iche.sco.respository.AppUserRepository;
import com.iche.sco.respository.MerchantRepository;
import com.iche.sco.utils.PaystackUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final AdminRepository adminRepository;

    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return email -> {
            Optional<Merchant> merchant = merchantRepository.findByEmail(email);
            if (merchant.isPresent()) {
                return merchant.get();
            }
            Optional<AppUser> appUser = appUserRepository.findByEmail(email);
            if (appUser.isPresent()) {
                return appUser.get();
            }
            return adminRepository.findByEmail(email).
                    orElseThrow(() -> new UsernameNotFoundException("user not found"));
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public HttpHeaders httpHeaders(){
        return new HttpHeaders();
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public PaystackUtils paystackUtils(){
        return new PaystackUtils();
    }
}
