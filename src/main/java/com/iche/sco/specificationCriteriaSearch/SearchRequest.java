package com.iche.sco.specificationCriteriaSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SearchRequest {
    private List<SearchSpecification> searchSpecifications;
    private String overAllOperation;
}
