package com.example.referentiel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.referentiel.model.Ssm;

import java.util.List;

@Repository
public interface SsmRepository extends JpaRepository<Ssm, Long> {
    List<Ssm> findByAccountId(Long ssmId);
}
