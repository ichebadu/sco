package com.iche.sco.search.criteriaSearch;


import com.iche.sco.search.specificationCriteriaSearch.DrugPageConstant;
import com.iche.sco.model.Drugs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class DrugCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public DrugCriteriaRepository(EntityManager entityManager){
        this.entityManager =entityManager;
        this.criteriaBuilder =entityManager.getCriteriaBuilder();
    }

    public Page<Drugs> findAllWithFilter(DrugPageConstant drugPageConstant, DrugCriteriaSearch drugCriteriaSearchRequest){
        CriteriaQuery<Drugs> criteriaQuery = criteriaBuilder.createQuery(Drugs.class);

        // equivalent * from drugs.
        // specifies the entity from which you want to retrieve the result
        Root<Drugs> drugsRoot = criteriaQuery.from(Drugs.class);

        // prepare WHERE clause
        //WHERE name like '%name%'
        Predicate predicate =getPredicate(drugCriteriaSearchRequest, drugsRoot);
        criteriaQuery.where(predicate);
        setOrder(drugPageConstant,criteriaQuery,drugsRoot);


        TypedQuery<Drugs> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(drugPageConstant.getPageNo() * drugPageConstant.getPageSize());
        typedQuery.setMaxResults(drugPageConstant.getPageSize());

        Pageable pageable = getPageable(drugPageConstant);

        long drugsCount = getDrugsCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(),pageable,drugsCount);
    }

    private long getDrugsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Drugs> countRoot = countQuery.from(Drugs.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(DrugPageConstant drugPageConstant) {
        Sort sort = Sort.by(drugPageConstant.getSortDirection(), drugPageConstant.getSortBy());

        return PageRequest.of(drugPageConstant.getPageNo(),drugPageConstant.getPageSize(),sort);
    }

    private Predicate getPredicate(DrugCriteriaSearch drugCriteriaSearch, Root<Drugs> drugsRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(drugCriteriaSearch.getDrugName())){
            predicates.add(
                    criteriaBuilder.like(drugsRoot.get("drugName"),
                            "%" + drugCriteriaSearch.getDrugName() + "%")
            );
        }
        if (Objects.nonNull(drugCriteriaSearch.getPrice())) {
            BigDecimal price = new BigDecimal(String.valueOf(drugCriteriaSearch.getPrice()));
            predicates.add(criteriaBuilder.equal(drugsRoot.get("price"), price));
        }
//        if(Objects.nonNull(drugCriteriaSearch.getDrugCategory())){
//            predicates.add(
//                    criteriaBuilder.like(drugsRoot.get
//                            ("drugCategory"),
//                            "%" + drugCriteriaSearch.getDrugCategory()+ "%")
//            );
//        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(DrugPageConstant drugPageConstant, CriteriaQuery<Drugs> criteriaQuery,
                          Root<Drugs> drugsRoot)
    {
        if(drugPageConstant.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(drugsRoot.get(drugPageConstant.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(drugsRoot.get(drugPageConstant.getSortBy())));
        }
    }
}
