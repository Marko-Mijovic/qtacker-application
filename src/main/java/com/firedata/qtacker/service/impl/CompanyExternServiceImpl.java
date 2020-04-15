package com.firedata.qtacker.service.impl;

import com.firedata.qtacker.service.CompanyExternService;
import com.firedata.qtacker.domain.CompanyExtern;
import com.firedata.qtacker.repository.CompanyExternRepository;
import com.firedata.qtacker.repository.search.CompanyExternSearchRepository;
import com.firedata.qtacker.service.dto.CompanyExternDTO;
import com.firedata.qtacker.service.mapper.CompanyExternMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CompanyExtern}.
 */
@Service
@Transactional
public class CompanyExternServiceImpl implements CompanyExternService {

    private final Logger log = LoggerFactory.getLogger(CompanyExternServiceImpl.class);

    private final CompanyExternRepository companyExternRepository;

    private final CompanyExternMapper companyExternMapper;

    private final CompanyExternSearchRepository companyExternSearchRepository;

    public CompanyExternServiceImpl(CompanyExternRepository companyExternRepository, CompanyExternMapper companyExternMapper, CompanyExternSearchRepository companyExternSearchRepository) {
        this.companyExternRepository = companyExternRepository;
        this.companyExternMapper = companyExternMapper;
        this.companyExternSearchRepository = companyExternSearchRepository;
    }

    /**
     * Save a companyExtern.
     *
     * @param companyExternDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompanyExternDTO save(CompanyExternDTO companyExternDTO) {
        log.debug("Request to save CompanyExtern : {}", companyExternDTO);
        CompanyExtern companyExtern = companyExternMapper.toEntity(companyExternDTO);
        companyExtern = companyExternRepository.save(companyExtern);
        CompanyExternDTO result = companyExternMapper.toDto(companyExtern);
        companyExternSearchRepository.save(companyExtern);
        return result;
    }

    /**
     * Get all the companyExterns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyExternDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyExterns");
        return companyExternRepository.findAll(pageable)
            .map(companyExternMapper::toDto);
    }

    /**
     * Get one companyExtern by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyExternDTO> findOne(Long id) {
        log.debug("Request to get CompanyExtern : {}", id);
        return companyExternRepository.findById(id)
            .map(companyExternMapper::toDto);
    }

    /**
     * Delete the companyExtern by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyExtern : {}", id);
        companyExternRepository.deleteById(id);
        companyExternSearchRepository.deleteById(id);
    }

    /**
     * Search for the companyExtern corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyExternDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompanyExterns for query {}", query);
        return companyExternSearchRepository.search(queryStringQuery(query), pageable)
            .map(companyExternMapper::toDto);
    }
}
