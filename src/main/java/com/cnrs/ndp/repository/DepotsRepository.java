package com.cnrs.ndp.repository;

import com.cnrs.ndp.entity.Depots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DepotsRepository extends JpaRepository<Depots, Long> {

    Depots findByNomDepot(String depotName);

    List<Depots> findAllByDepotHumaNumOrderByDateDepot(String depotHumaNum);

}
