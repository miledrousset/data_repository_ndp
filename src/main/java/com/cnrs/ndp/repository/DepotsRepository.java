package com.cnrs.ndp.repository;

import com.cnrs.ndp.entity.Depots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepotsRepository extends JpaRepository<Depots, Long> {

    Depots findByNomDepot(String depotName);

}
