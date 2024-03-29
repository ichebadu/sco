package com.iche.sco.dto.drugs.request;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;



@Builder
public record DrugCreationRequest(
        @Column(name = "drugs_name", nullable = false)
        @NotNull(message = "enter drug name")
        String drugName,
        @Column(name = "merchant_id", nullable = false)
        @NotNull(message = "enter drug name")
        String merchantId,
        @Column(name = "user_name", nullable = false)
        @NotNull(message = "enter user name")
        String userEmail,
        @Column(name = "drugs_price", nullable = false)
        @NotNull(message = "enter drug price")
        BigDecimal price,
        @Column(name = "drugs_packs", nullable = false)
        @NotNull(message = "enter the number of pack")
        @DecimalMin(value = "1", inclusive = true, message = "Packs must be greater than or equal to 1")
        int packs
){
}
