package com.firedata.qtacker.repository;

import com.firedata.qtacker.domain.LogUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LogUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogUserRepository extends JpaRepository<LogUser, Long> {
}
