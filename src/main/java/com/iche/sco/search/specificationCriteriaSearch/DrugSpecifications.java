package com.iche.sco.search.specificationCriteriaSearch;

import com.iche.sco.model.Drugs;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DrugSpecifications {
    public static Specification<Drugs> byCriteria(SpecificationInput specificationInput) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (specificationInput != null) {
                if (specificationInput.getDrugName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("drugName"), "%" + specificationInput.getDrugName() + "%"));
                }
                if (specificationInput.getPrice() != null) {
                    // Convert the String representation of price to BigDecimal
                    BigDecimal price = new BigDecimal(String.valueOf(specificationInput.getPrice()));
                    predicates.add(criteriaBuilder.equal(root.get("price"), price));
                }
                if(specificationInput.getPacks() != 0){
                    predicates.add(criteriaBuilder.equal(root.get("packs"), specificationInput.getPacks()));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

