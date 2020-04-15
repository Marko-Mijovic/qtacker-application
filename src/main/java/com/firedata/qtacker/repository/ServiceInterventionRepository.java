package com.firedata.qtacker.repository;

import com.firedata.qtacker.domain.ServiceIntervention;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceIntervention entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceInterventionRepository extends JpaRepository<ServiceIntervention, Long> {
}
