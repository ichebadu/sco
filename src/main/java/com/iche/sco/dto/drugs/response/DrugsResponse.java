package com.iche.sco.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class DrugsResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private int packs;
}
