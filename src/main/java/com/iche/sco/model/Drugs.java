package com.iche.sco.model;


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

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;
//    @CreatedBy
//    @Column(
//            nullable = false,
//            updatable = false
//    )
//    private LocalDateTime createDate;
//    @LastModifiedBy
//    @Column(
//            insertable = false
//    )
//    private LocalDateTime lastModified;
}
