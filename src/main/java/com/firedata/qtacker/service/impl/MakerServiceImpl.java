package com.firedata.qtacker.service.impl;

import com.firedata.qtacker.service.MakerService;
import com.firedata.qtacker.domain.Maker;
import com.firedata.qtacker.repository.MakerRepository;
import com.firedata.qtacker.repository.search.MakerSearchRepository;
import com.firedata.qtacker.service.dto.MakerDTO;
import com.firedata.qtacker.service.mapper.MakerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Maker}.
 */
@Service
@Transactional
public class MakerServiceImpl implements MakerService {

    private final Logger log = LoggerFactory.getLogger(MakerServiceImpl.class);

    private final MakerRepository makerRepository;

    private final MakerMapper makerMapper;

    private final MakerSearchRepository makerSearchRepository;

    public MakerServiceImpl(MakerRepository makerRepository, MakerMapper makerMapper, MakerSearchRepository makerSearchRepository) {
        this.makerRepository = makerRepository;
        this.makerMapper = makerMapper;
        this.makerSearchRepository = makerSearchRepository;
    }

    /**
     * Save a maker.
     *
     * @param makerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MakerDTO save(MakerDTO makerDTO) {
        log.debug("Request to save Maker : {}", makerDTO);
        Maker maker = makerMapper.toEntity(makerDTO);
        maker = makerRepository.save(maker);
        MakerDTO result = makerMapper.toDto(maker);
        makerSearchRepository.save(maker);
        return result;
    }

    /**
     * Get all the makers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MakerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Makers");
        return makerRepository.findAll(pageable)
            .map(makerMapper::toDto);
    }

    /**
     * Get one maker by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MakerDTO> findOne(Long id) {
        log.debug("Request to get Maker : {}", id);
        return makerRepository.findById(id)
            .map(makerMapper::toDto);
    }

    /**
     * Delete the maker by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Maker : {}", id);
        makerRepository.deleteById(id);
        makerSearchRepository.deleteById(id);
    }

    /**
     * Search for the maker corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MakerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Makers for query {}", query);
        return makerSearchRepository.search(queryStringQuery(query), pageable)
            .map(makerMapper::toDto);
    }
}
