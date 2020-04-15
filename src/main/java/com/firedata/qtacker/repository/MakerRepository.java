package com.firedata.qtacker.repository;

import com.firedata.qtacker.domain.Maker;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Maker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {
}
