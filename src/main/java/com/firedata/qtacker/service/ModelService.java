package com.firedata.qtacker.service;

import com.firedata.qtacker.service.dto.ModelDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.firedata.qtacker.domain.Model}.
 */
public interface ModelService {

    /**
     * Save a model.
     *
     * @param modelDTO the entity to save.
     * @return the persisted entity.
     */
    ModelDTO save(ModelDTO modelDTO);

    /**
     * Get all the models.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" model.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModelDTO> findOne(Long id);

    /**
     * Delete the "id" model.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the model corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModelDTO> search(String query, Pageable pageable);
}
