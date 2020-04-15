package com.firedata.qtacker.service.impl;

import com.firedata.qtacker.service.ModelService;
import com.firedata.qtacker.domain.Model;
import com.firedata.qtacker.repository.ModelRepository;
import com.firedata.qtacker.repository.search.ModelSearchRepository;
import com.firedata.qtacker.service.dto.ModelDTO;
import com.firedata.qtacker.service.mapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Model}.
 */
@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    private final Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    private final ModelRepository modelRepository;

    private final ModelMapper modelMapper;

    private final ModelSearchRepository modelSearchRepository;

    public ModelServiceImpl(ModelRepository modelRepository, ModelMapper modelMapper, ModelSearchRepository modelSearchRepository) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
        this.modelSearchRepository = modelSearchRepository;
    }

    /**
     * Save a model.
     *
     * @param modelDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ModelDTO save(ModelDTO modelDTO) {
        log.debug("Request to save Model : {}", modelDTO);
        Model model = modelMapper.toEntity(modelDTO);
        model = modelRepository.save(model);
        ModelDTO result = modelMapper.toDto(model);
        modelSearchRepository.save(model);
        return result;
    }

    /**
     * Get all the models.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Models");
        return modelRepository.findAll(pageable)
            .map(modelMapper::toDto);
    }

    /**
     * Get one model by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModelDTO> findOne(Long id) {
        log.debug("Request to get Model : {}", id);
        return modelRepository.findById(id)
            .map(modelMapper::toDto);
    }

    /**
     * Delete the model by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Model : {}", id);
        modelRepository.deleteById(id);
        modelSearchRepository.deleteById(id);
    }

    /**
     * Search for the model corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Models for query {}", query);
        return modelSearchRepository.search(queryStringQuery(query), pageable)
            .map(modelMapper::toDto);
    }
}
