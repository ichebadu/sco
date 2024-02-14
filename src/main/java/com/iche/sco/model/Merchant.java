package com.iche.sco.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iche.sco.enums.City;
import com.iche.sco.enums.Country;
import com.iche.sco.enums.MerchantType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@DiscriminatorValue("merchant")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Merchant extends BaseUser{
    private String companyName;
    private String address1;
    private String address2;
    private String PostalCode;
    @Enumerated(EnumType.STRING)
    private City city;
    @Enumerated(EnumType.STRING)
    private Country country;
    @OneToMany(mappedBy = "merchant",fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<Drugs> drugs;
    @Enumerated(EnumType.STRING)
    private MerchantType merchantType;
}
