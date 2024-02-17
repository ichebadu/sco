package com.iche.sco.specificationCriteriaSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CriteriaSearchOperation {
    private String columnName;
    private String value;
    private String sortColumn;
    private String operation;
    private String sortOrder;
    private Integer pageSize;
    private Integer pageNumber;
}
