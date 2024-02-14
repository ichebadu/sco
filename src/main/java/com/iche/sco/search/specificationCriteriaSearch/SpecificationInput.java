package com.iche.sco.search.specificationCriteriaSearch;

import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SpecificationInput {
    private String drugName;
    private String merchantId;
    private String userEmail;
    private BigDecimal price;
    private int packs;
}
