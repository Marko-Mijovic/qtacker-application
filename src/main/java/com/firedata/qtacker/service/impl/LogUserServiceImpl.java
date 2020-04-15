package com.firedata.qtacker.service.impl;

import com.firedata.qtacker.service.LogUserService;
import com.firedata.qtacker.domain.LogUser;
import com.firedata.qtacker.repository.LogUserRepository;
import com.firedata.qtacker.repository.search.LogUserSearchRepository;
import com.firedata.qtacker.service.dto.LogUserDTO;
import com.firedata.qtacker.service.mapper.LogUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LogUser}.
 */
@Service
@Transactional
public class LogUserServiceImpl implements LogUserService {

    private final Logger log = LoggerFactory.getLogger(LogUserServiceImpl.class);

    private final LogUserRepository logUserRepository;

    private final LogUserMapper logUserMapper;

    private final LogUserSearchRepository logUserSearchRepository;

    public LogUserServiceImpl(LogUserRepository logUserRepository, LogUserMapper logUserMapper, LogUserSearchRepository logUserSearchRepository) {
        this.logUserRepository = logUserRepository;
        this.logUserMapper = logUserMapper;
        this.logUserSearchRepository = logUserSearchRepository;
    }

    /**
     * Save a logUser.
     *
     * @param logUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LogUserDTO save(LogUserDTO logUserDTO) {
        log.debug("Request to save LogUser : {}", logUserDTO);
        LogUser logUser = logUserMapper.toEntity(logUserDTO);
        logUser = logUserRepository.save(logUser);
        LogUserDTO result = logUserMapper.toDto(logUser);
        logUserSearchRepository.save(logUser);
        return result;
    }

    /**
     * Get all the logUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LogUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LogUsers");
        return logUserRepository.findAll(pageable)
            .map(logUserMapper::toDto);
    }

    /**
     * Get one logUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LogUserDTO> findOne(Long id) {
        log.debug("Request to get LogUser : {}", id);
        return logUserRepository.findById(id)
            .map(logUserMapper::toDto);
    }

    /**
     * Delete the logUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogUser : {}", id);
        logUserRepository.deleteById(id);
        logUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the logUser corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LogUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LogUsers for query {}", query);
        return logUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(logUserMapper::toDto);
    }
}
