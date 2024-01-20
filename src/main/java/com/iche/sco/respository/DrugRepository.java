package com.iche.sco.respository;

import com.iche.sco.model.Drugs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drugs, Long> {

    boolean existsByName(String name);
    List<Drugs> findAll();

}
