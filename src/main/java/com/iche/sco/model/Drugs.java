package com.iche.sco.model;

import com.iche.sco.enums.PaymentType;
import com.iche.sco.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Drugs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "drugs_name", nullable = false)
    private String name;
    @Column(name = "drugs_price", nullable = false)
    private BigDecimal price;
    @Column(name = "drugs_packs", nullable = false)
    private int packs;

}
