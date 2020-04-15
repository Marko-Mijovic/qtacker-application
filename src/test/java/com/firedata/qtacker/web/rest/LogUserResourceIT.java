package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.QtackerApplicationApp;
import com.firedata.qtacker.config.TestSecurityConfiguration;
import com.firedata.qtacker.domain.LogUser;
import com.firedata.qtacker.repository.LogUserRepository;
import com.firedata.qtacker.repository.search.LogUserSearchRepository;
import com.firedata.qtacker.service.LogUserService;
import com.firedata.qtacker.service.dto.LogUserDTO;
import com.firedata.qtacker.service.mapper.LogUserMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LogUserResource} REST controller.
 */
@SpringBootTest(classes = { QtackerApplicationApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LogUserResourceIT {

    private static final Instant DEFAULT_DATE_TIME_LOG = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME_LOG = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LogUserRepository logUserRepository;

    @Autowired
    private LogUserMapper logUserMapper;

    @Autowired
    private LogUserService logUserService;

    /**
     * This repository is mocked in the com.firedata.qtacker.repository.search test package.
     *
     * @see com.firedata.qtacker.repository.search.LogUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private LogUserSearchRepository mockLogUserSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogUserMockMvc;

    private LogUser logUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogUser createEntity(EntityManager em) {
        LogUser logUser = new LogUser()
            .dateTimeLog(DEFAULT_DATE_TIME_LOG);
        return logUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogUser createUpdatedEntity(EntityManager em) {
        LogUser logUser = new LogUser()
            .dateTimeLog(UPDATED_DATE_TIME_LOG);
        return logUser;
    }

    @BeforeEach
    public void initTest() {
        logUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogUser() throws Exception {
        int databaseSizeBeforeCreate = logUserRepository.findAll().size();

        // Create the LogUser
        LogUserDTO logUserDTO = logUserMapper.toDto(logUser);
        restLogUserMockMvc.perform(post("/api/log-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logUserDTO)))
            .andExpect(status().isCreated());

        // Validate the LogUser in the database
        List<LogUser> logUserList = logUserRepository.findAll();
        assertThat(logUserList).hasSize(databaseSizeBeforeCreate + 1);
        LogUser testLogUser = logUserList.get(logUserList.size() - 1);
        assertThat(testLogUser.getDateTimeLog()).isEqualTo(DEFAULT_DATE_TIME_LOG);

        // Validate the LogUser in Elasticsearch
        verify(mockLogUserSearchRepository, times(1)).save(testLogUser);
    }

    @Test
    @Transactional
    public void createLogUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logUserRepository.findAll().size();

        // Create the LogUser with an existing ID
        logUser.setId(1L);
        LogUserDTO logUserDTO = logUserMapper.toDto(logUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogUserMockMvc.perform(post("/api/log-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogUser in the database
        List<LogUser> logUserList = logUserRepository.findAll();
        assertThat(logUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the LogUser in Elasticsearch
        verify(mockLogUserSearchRepository, times(0)).save(logUser);
    }


    @Test
    @Transactional
    public void getAllLogUsers() throws Exception {
        // Initialize the database
        logUserRepository.saveAndFlush(logUser);

        // Get all the logUserList
        restLogUserMockMvc.perform(get("/api/log-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTimeLog").value(hasItem(DEFAULT_DATE_TIME_LOG.toString())));
    }
    
    @Test
    @Transactional
    public void getLogUser() throws Exception {
        // Initialize the database
        logUserRepository.saveAndFlush(logUser);

        // Get the logUser
        restLogUserMockMvc.perform(get("/api/log-users/{id}", logUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logUser.getId().intValue()))
            .andExpect(jsonPath("$.dateTimeLog").value(DEFAULT_DATE_TIME_LOG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogUser() throws Exception {
        // Get the logUser
        restLogUserMockMvc.perform(get("/api/log-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogUser() throws Exception {
        // Initialize the database
        logUserRepository.saveAndFlush(logUser);

        int databaseSizeBeforeUpdate = logUserRepository.findAll().size();

        // Update the logUser
        LogUser updatedLogUser = logUserRepository.findById(logUser.getId()).get();
        // Disconnect from session so that the updates on updatedLogUser are not directly saved in db
        em.detach(updatedLogUser);
        updatedLogUser
            .dateTimeLog(UPDATED_DATE_TIME_LOG);
        LogUserDTO logUserDTO = logUserMapper.toDto(updatedLogUser);

        restLogUserMockMvc.perform(put("/api/log-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logUserDTO)))
            .andExpect(status().isOk());

        // Validate the LogUser in the database
        List<LogUser> logUserList = logUserRepository.findAll();
        assertThat(logUserList).hasSize(databaseSizeBeforeUpdate);
        LogUser testLogUser = logUserList.get(logUserList.size() - 1);
        assertThat(testLogUser.getDateTimeLog()).isEqualTo(UPDATED_DATE_TIME_LOG);

        // Validate the LogUser in Elasticsearch
        verify(mockLogUserSearchRepository, times(1)).save(testLogUser);
    }

    @Test
    @Transactional
    public void updateNonExistingLogUser() throws Exception {
        int databaseSizeBeforeUpdate = logUserRepository.findAll().size();

        // Create the LogUser
        LogUserDTO logUserDTO = logUserMapper.toDto(logUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogUserMockMvc.perform(put("/api/log-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogUser in the database
        List<LogUser> logUserList = logUserRepository.findAll();
        assertThat(logUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LogUser in Elasticsearch
        verify(mockLogUserSearchRepository, times(0)).save(logUser);
    }

    @Test
    @Transactional
    public void deleteLogUser() throws Exception {
        // Initialize the database
        logUserRepository.saveAndFlush(logUser);

        int databaseSizeBeforeDelete = logUserRepository.findAll().size();

        // Delete the logUser
        restLogUserMockMvc.perform(delete("/api/log-users/{id}", logUser.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogUser> logUserList = logUserRepository.findAll();
        assertThat(logUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LogUser in Elasticsearch
        verify(mockLogUserSearchRepository, times(1)).deleteById(logUser.getId());
    }

    @Test
    @Transactional
    public void searchLogUser() throws Exception {
        // Initialize the database
        logUserRepository.saveAndFlush(logUser);
        when(mockLogUserSearchRepository.search(queryStringQuery("id:" + logUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(logUser), PageRequest.of(0, 1), 1));
        // Search the logUser
        restLogUserMockMvc.perform(get("/api/_search/log-users?query=id:" + logUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTimeLog").value(hasItem(DEFAULT_DATE_TIME_LOG.toString())));
    }
}
