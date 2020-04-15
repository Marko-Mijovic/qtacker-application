package com.firedata.qtacker.service;

import com.firedata.qtacker.service.dto.CompanyExternDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.firedata.qtacker.domain.CompanyExtern}.
 */
public interface CompanyExternService {

    /**
     * Save a companyExtern.
     *
     * @param companyExternDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyExternDTO save(CompanyExternDTO companyExternDTO);

    /**
     * Get all the companyExterns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyExternDTO> findAll(Pageable pageable);

    /**
     * Get the "id" companyExtern.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyExternDTO> findOne(Long id);

    /**
     * Delete the "id" companyExtern.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the companyExtern corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyExternDTO> search(String query, Pageable pageable);
}
