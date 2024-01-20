package com.iche.sco.dto.request;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;



@Builder
public record DrugCreationRequest(
        @Column(name = "drugs_name", nullable = false)
        @NotNull(message = "enter drug name")
        String name,
        @Column(name = "drugs_price", nullable = false)
        @NotNull(message = "enter drug price")
        BigDecimal price,
        @Column(name = "drugs_packs", nullable = false)
        @NotNull(message = "enter the number of pack")
        @DecimalMin(value = "1", inclusive = true, message = "Packs must be greater than or equal to 1")
        int packs
){
}
