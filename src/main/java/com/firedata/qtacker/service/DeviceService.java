package com.firedata.qtacker.service;

import com.firedata.qtacker.service.dto.DeviceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.firedata.qtacker.domain.Device}.
 */
public interface DeviceService {

    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceDTO save(DeviceDTO deviceDTO);

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" device.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceDTO> findOne(Long id);

    /**
     * Delete the "id" device.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the device corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceDTO> search(String query, Pageable pageable);
}
