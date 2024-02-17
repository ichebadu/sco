package com.iche.sco.utils;

import com.iche.sco.dto.user.request.MerchantRegistrationRequest;
import com.iche.sco.dto.user.request.AppUserRegistrationRequest;
import com.iche.sco.enums.MerchantType;
import com.iche.sco.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@Component
public class MapUtils {
    public Merchant mapRequestToMerchant(MerchantRegistrationRequest registrationRequest, String phoneNumber) {
            Merchant merchant = new Merchant();
            merchant.setFirstName(registrationRequest.getFirstName());
            merchant.setLastName(registrationRequest.getLastName());
            merchant.setEmail(registrationRequest.getEmail());
            merchant.setPhoneNumber(phoneNumber);
            merchant.setStatus(false);
            merchant.setMerchantType(MerchantType.valueOf(registrationRequest.getMerchantType()));
            return merchant;
        }
    public AppUser mapRequestToAppUser(AppUserRegistrationRequest appUserRegistrationRequest, String phoneNumber) {
        AppUser appUser = new AppUser();
        appUser.setFirstName(appUserRegistrationRequest.getFirstName());
        appUser.setLastName(appUserRegistrationRequest.getLastName());
        appUser.setEmail(appUserRegistrationRequest.getEmail());
        appUser.setPhoneNumber(phoneNumber);
        appUser.setStatus(false);
        return appUser;
    }
    public static Drugs drugsMap(Drugs drug) {
        Drugs drugs = new Drugs();
        drugs.setDrugName(drug.getDrugName());
        drugs.setPrice(drug.getPrice());
        drugs.setPacks(drug.getPacks());
        drugs.setUpdateAt(drug.getUpdateAt());
        return drugs;
    }
}
