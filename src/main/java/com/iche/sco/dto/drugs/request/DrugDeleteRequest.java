package com.iche.sco.dto.drugs.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record DrugDeleteRequest (
        @Column(name = "drug_id", nullable = false)
        Long id,
        @Column(name = "user_name", nullable = false)
        @NotNull(message = "enter user name")
        String userEmail
) {
}
