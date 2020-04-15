package com.firedata.qtacker.service;

import com.firedata.qtacker.service.dto.ServiceInterventionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.firedata.qtacker.domain.ServiceIntervention}.
 */
public interface ServiceInterventionService {

    /**
     * Save a serviceIntervention.
     *
     * @param serviceInterventionDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceInterventionDTO save(ServiceInterventionDTO serviceInterventionDTO);

    /**
     * Get all the serviceInterventions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceInterventionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" serviceIntervention.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceInterventionDTO> findOne(Long id);

    /**
     * Delete the "id" serviceIntervention.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the serviceIntervention corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceInterventionDTO> search(String query, Pageable pageable);
}
