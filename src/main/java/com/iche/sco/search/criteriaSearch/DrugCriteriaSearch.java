package com.iche.sco.search.criteriaSearch;

//import com.iche.sco.enums.DrugCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//import java.math.BigDecimal;

@Setter
@Getter
public class DrugCriteriaSearch {

    private String drugName;
    private String label;
    private BigDecimal price;
//    private DrugCategory drugCategory;
}
