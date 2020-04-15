package com.firedata.qtacker.service;

import com.firedata.qtacker.service.dto.LogUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.firedata.qtacker.domain.LogUser}.
 */
public interface LogUserService {

    /**
     * Save a logUser.
     *
     * @param logUserDTO the entity to save.
     * @return the persisted entity.
     */
    LogUserDTO save(LogUserDTO logUserDTO);

    /**
     * Get all the logUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LogUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" logUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LogUserDTO> findOne(Long id);

    /**
     * Delete the "id" logUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the logUser corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LogUserDTO> search(String query, Pageable pageable);
}
