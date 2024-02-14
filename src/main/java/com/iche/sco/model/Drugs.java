package com.iche.sco.model;

import com.iche.sco.enums.DrugCategory;
import com.iche.sco.enums.DrugStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDateTime createDate; // New field for createDate


    @Column(name = "updatedDate")
    private LocalDateTime updateAt; // New field for createDate

    // Setter method for createDate


//    public Drugs(Long id, String drugName, String label, BigDecimal price, DrugCategory drugCategory, int packs, Merchant merchant, DrugStatus drugStatus) {
//        this.id = id;
//        this.drugName = drugName;
//        this.label = label;
//        this.price = price;
//        this.drugCategory = drugCategory;
//        this.packs = packs;
//        this.merchant = merchant;
//        this.drugStatus = drugStatus;
//        this.createDate = Date.from(Instant.now());
//    }

}
