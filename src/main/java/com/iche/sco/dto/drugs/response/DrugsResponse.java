package com.iche.sco.dto.drugs.response;

import com.iche.sco.enums.DrugStatus;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class DrugsResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private int packs;
    private String merchantId;
    private DrugStatus drugStatus;
    private String createDate;
    private String updateDate;


}
