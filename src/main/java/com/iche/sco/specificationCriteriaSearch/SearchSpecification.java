package com.iche.sco.specificationCriteriaSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SearchSpecification {
    private String columnName;
    private String value;
    private String operation;
    private String joinTable;
    private String sortColumn;
    private String sortOrder;
    private Integer pageSize;
    private Integer pageNumber;
}
