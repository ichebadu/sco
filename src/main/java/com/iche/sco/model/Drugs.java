package com.iche.sco.model;

import com.iche.sco.enums.DrugCategory;
import com.iche.sco.enums.DrugStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "drugs")
@Data
@ToString
public class Drugs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String drugName;
    private String label;
    @Column(name = "drugs_price", nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private DrugCategory drugCategory;

    @Column(name = "drugs_packs", nullable = false)
    private int packs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    private DrugStatus drugStatus;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "updatedDate")
    private LocalDateTime updateAt;

}
