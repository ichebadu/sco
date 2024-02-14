package com.iche.sco.search.specificationCriteriaSearch;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DrugPageConstant {
    private int pageNo = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";
}
