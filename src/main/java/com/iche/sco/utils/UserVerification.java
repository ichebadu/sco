package com.iche.sco.utils;


import com.iche.sco.exception.UserAlreadyExistsException;
import com.iche.sco.exception.UserNotFoundException;

import com.iche.sco.model.Admin;
import com.iche.sco.model.AppUser;
import com.iche.sco.model.BaseUser;
import com.iche.sco.model.Merchant;
import com.iche.sco.respository.AdminRepository;
import com.iche.sco.respository.AppUserRepository;
import com.iche.sco.respository.MerchantRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Data
@RequiredArgsConstructor
@Component
public class UserVerification {
    private final AdminRepository adminRepository;
    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;

    public BaseUser verifyUserByEmail(String email){
        BaseUser baseUser;
        if (appUserRepository.existsByEmail(email)){
            baseUser = appUserRepository.findByEmail(email).orElseThrow(
                    ()-> new UserNotFoundException("App user not found")
            );
        } else if(adminRepository.existsByEmail(email)){
            baseUser = adminRepository.findByEmail(email).orElseThrow(
                    ()-> new UserNotFoundException("Admin not found"));
        } else if(merchantRepository.existsByEmail(email)){
            baseUser = merchantRepository.findByEmail(email).orElseThrow(
                    ()-> new UserNotFoundException("Admin not found"));
        }else{
            throw new UserNotFoundException("User not Found");
    }
        return baseUser;
    }
    public void checkUserExists(String email) {
        if (appUserRepository.existsByEmail(email) ||
                adminRepository.existsByEmail(email) ||
                merchantRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }
    }


    public String getUserEmailFromContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    public BaseUser validateLoginUser(String email){
        BaseUser baseUser = verifyUserByEmail(email);
        if(!baseUser.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new UsernameNotFoundException("this is not the log in user");
        }
        return baseUser;
    }
    public BaseUser getLoginUser(){
        String loginUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return verifyUserByEmail(loginUser);
    }
    public BaseUser saveUser(BaseUser currentUser){
        BaseUser baseUser = verifyUserByEmail(currentUser.getEmail());
        if (baseUser instanceof Admin admin){
            return adminRepository.save(admin);
        } else if(baseUser instanceof Merchant merchant){
            return merchantRepository.save(merchant);
        } else if(baseUser instanceof AppUser appUser){
            return appUserRepository.save(appUser);
        }else{
            throw new UserNotFoundException("User not Found");
        }
    }

   public boolean isPhoneNumberRegistered(String formattedPhoneNumber) {
        boolean existsInAdmin = adminRepository.existsByPhoneNumber(formattedPhoneNumber);
        boolean existsInMerchant = merchantRepository.existsByPhoneNumber(formattedPhoneNumber);
        boolean existsInAppUser = appUserRepository.existsByPhoneNumber(formattedPhoneNumber);
        return existsInAdmin || existsInAppUser || existsInMerchant;
    }
}
