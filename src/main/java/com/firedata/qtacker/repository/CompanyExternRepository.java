package com.firedata.qtacker.repository;

import com.firedata.qtacker.domain.CompanyExtern;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyExtern entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyExternRepository extends JpaRepository<CompanyExtern, Long> {
}
