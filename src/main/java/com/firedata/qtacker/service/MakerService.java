package com.firedata.qtacker.service;

import com.firedata.qtacker.service.dto.MakerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.firedata.qtacker.domain.Maker}.
 */
public interface MakerService {

    /**
     * Save a maker.
     *
     * @param makerDTO the entity to save.
     * @return the persisted entity.
     */
    MakerDTO save(MakerDTO makerDTO);

    /**
     * Get all the makers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MakerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" maker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MakerDTO> findOne(Long id);

    /**
     * Delete the "id" maker.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the maker corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MakerDTO> search(String query, Pageable pageable);
}
