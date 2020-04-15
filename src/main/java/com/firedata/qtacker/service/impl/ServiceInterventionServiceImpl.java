package com.firedata.qtacker.service.impl;

import com.firedata.qtacker.service.ServiceInterventionService;
import com.firedata.qtacker.domain.ServiceIntervention;
import com.firedata.qtacker.repository.ServiceInterventionRepository;
import com.firedata.qtacker.repository.search.ServiceInterventionSearchRepository;
import com.firedata.qtacker.service.dto.ServiceInterventionDTO;
import com.firedata.qtacker.service.mapper.ServiceInterventionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ServiceIntervention}.
 */
@Service
@Transactional
public class ServiceInterventionServiceImpl implements ServiceInterventionService {

    private final Logger log = LoggerFactory.getLogger(ServiceInterventionServiceImpl.class);

    private final ServiceInterventionRepository serviceInterventionRepository;

    private final ServiceInterventionMapper serviceInterventionMapper;

    private final ServiceInterventionSearchRepository serviceInterventionSearchRepository;

    public ServiceInterventionServiceImpl(ServiceInterventionRepository serviceInterventionRepository, ServiceInterventionMapper serviceInterventionMapper, ServiceInterventionSearchRepository serviceInterventionSearchRepository) {
        this.serviceInterventionRepository = serviceInterventionRepository;
        this.serviceInterventionMapper = serviceInterventionMapper;
        this.serviceInterventionSearchRepository = serviceInterventionSearchRepository;
    }

    /**
     * Save a serviceIntervention.
     *
     * @param serviceInterventionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceInterventionDTO save(ServiceInterventionDTO serviceInterventionDTO) {
        log.debug("Request to save ServiceIntervention : {}", serviceInterventionDTO);
        ServiceIntervention serviceIntervention = serviceInterventionMapper.toEntity(serviceInterventionDTO);
        serviceIntervention = serviceInterventionRepository.save(serviceIntervention);
        ServiceInterventionDTO result = serviceInterventionMapper.toDto(serviceIntervention);
        serviceInterventionSearchRepository.save(serviceIntervention);
        return result;
    }

    /**
     * Get all the serviceInterventions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceInterventionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceInterventions");
        return serviceInterventionRepository.findAll(pageable)
            .map(serviceInterventionMapper::toDto);
    }

    /**
     * Get one serviceIntervention by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceInterventionDTO> findOne(Long id) {
        log.debug("Request to get ServiceIntervention : {}", id);
        return serviceInterventionRepository.findById(id)
            .map(serviceInterventionMapper::toDto);
    }

    /**
     * Delete the serviceIntervention by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceIntervention : {}", id);
        serviceInterventionRepository.deleteById(id);
        serviceInterventionSearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceIntervention corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceInterventionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceInterventions for query {}", query);
        return serviceInterventionSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceInterventionMapper::toDto);
    }
}
