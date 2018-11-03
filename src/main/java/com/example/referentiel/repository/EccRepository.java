package com.example.referentiel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.referentiel.model.Ecc;

import java.util.List;

@Repository
public interface EccRepository extends JpaRepository<Ecc, Long> {
    //List<Route> findByRouteTableId(Long routeTableId);
}
