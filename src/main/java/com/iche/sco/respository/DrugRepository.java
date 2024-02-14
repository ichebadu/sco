package com.iche.sco.respository;

import com.iche.sco.model.Drugs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DrugRepository extends JpaRepository<Drugs, Long>, JpaSpecificationExecutor<Drugs> {

    boolean existsByDrugName(String name);
    List<Drugs> findAll();
    Page<Drugs> findAll(Pageable pageable);

}
