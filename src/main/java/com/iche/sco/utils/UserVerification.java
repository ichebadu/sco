package com.iche.sco.utils;


import com.iche.sco.exception.UserNotFoundException;
import com.iche.sco.model.Users;
import com.iche.sco.respository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Data
@RequiredArgsConstructor
@Component
public class UserVerification {
    private final UserRepository userRepository;

    public Users verifyUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not Found"));
    }
    public String getUserEmailFromContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Users validateLoginUser(String email){
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not Found"));

        if(!user.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new UsernameNotFoundException("this is not the log in user");
        }
        return user;
    }
}
