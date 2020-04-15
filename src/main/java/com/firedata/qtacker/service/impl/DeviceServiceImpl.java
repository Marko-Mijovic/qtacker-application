package com.firedata.qtacker.service.impl;

import com.firedata.qtacker.service.DeviceService;
import com.firedata.qtacker.domain.Device;
import com.firedata.qtacker.repository.DeviceRepository;
import com.firedata.qtacker.repository.search.DeviceSearchRepository;
import com.firedata.qtacker.service.dto.DeviceDTO;
import com.firedata.qtacker.service.mapper.DeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Device}.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    private final DeviceSearchRepository deviceSearchRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper, DeviceSearchRepository deviceSearchRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
        this.deviceSearchRepository = deviceSearchRepository;
    }

    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        log.debug("Request to save Device : {}", deviceDTO);
        Device device = deviceMapper.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        DeviceDTO result = deviceMapper.toDto(device);
        deviceSearchRepository.save(device);
        return result;
    }

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable)
            .map(deviceMapper::toDto);
    }

    /**
     * Get one device by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceDTO> findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        return deviceRepository.findById(id)
            .map(deviceMapper::toDto);
    }

    /**
     * Delete the device by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.deleteById(id);
        deviceSearchRepository.deleteById(id);
    }

    /**
     * Search for the device corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Devices for query {}", query);
        return deviceSearchRepository.search(queryStringQuery(query), pageable)
            .map(deviceMapper::toDto);
    }
}
