package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.QtackerApplicationApp;
import com.firedata.qtacker.config.TestSecurityConfiguration;
import com.firedata.qtacker.domain.ServiceIntervention;
import com.firedata.qtacker.repository.ServiceInterventionRepository;
import com.firedata.qtacker.repository.search.ServiceInterventionSearchRepository;
import com.firedata.qtacker.service.ServiceInterventionService;
import com.firedata.qtacker.service.dto.ServiceInterventionDTO;
import com.firedata.qtacker.service.mapper.ServiceInterventionMapper;

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
 * Integration tests for the {@link ServiceInterventionResource} REST controller.
 */
@SpringBootTest(classes = { QtackerApplicationApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceInterventionResourceIT {

    private static final Instant DEFAULT_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ServiceInterventionRepository serviceInterventionRepository;

    @Autowired
    private ServiceInterventionMapper serviceInterventionMapper;

    @Autowired
    private ServiceInterventionService serviceInterventionService;

    /**
     * This repository is mocked in the com.firedata.qtacker.repository.search test package.
     *
     * @see com.firedata.qtacker.repository.search.ServiceInterventionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceInterventionSearchRepository mockServiceInterventionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceInterventionMockMvc;

    private ServiceIntervention serviceIntervention;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceIntervention createEntity(EntityManager em) {
        ServiceIntervention serviceIntervention = new ServiceIntervention()
            .dateTime(DEFAULT_DATE_TIME)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION);
        return serviceIntervention;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceIntervention createUpdatedEntity(EntityManager em) {
        ServiceIntervention serviceIntervention = new ServiceIntervention()
            .dateTime(UPDATED_DATE_TIME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION);
        return serviceIntervention;
    }

    @BeforeEach
    public void initTest() {
        serviceIntervention = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceIntervention() throws Exception {
        int databaseSizeBeforeCreate = serviceInterventionRepository.findAll().size();

        // Create the ServiceIntervention
        ServiceInterventionDTO serviceInterventionDTO = serviceInterventionMapper.toDto(serviceIntervention);
        restServiceInterventionMockMvc.perform(post("/api/service-interventions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceInterventionDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceIntervention in the database
        List<ServiceIntervention> serviceInterventionList = serviceInterventionRepository.findAll();
        assertThat(serviceInterventionList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceIntervention testServiceIntervention = serviceInterventionList.get(serviceInterventionList.size() - 1);
        assertThat(testServiceIntervention.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testServiceIntervention.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testServiceIntervention.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ServiceIntervention in Elasticsearch
        verify(mockServiceInterventionSearchRepository, times(1)).save(testServiceIntervention);
    }

    @Test
    @Transactional
    public void createServiceInterventionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceInterventionRepository.findAll().size();

        // Create the ServiceIntervention with an existing ID
        serviceIntervention.setId(1L);
        ServiceInterventionDTO serviceInterventionDTO = serviceInterventionMapper.toDto(serviceIntervention);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceInterventionMockMvc.perform(post("/api/service-interventions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceInterventionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceIntervention in the database
        List<ServiceIntervention> serviceInterventionList = serviceInterventionRepository.findAll();
        assertThat(serviceInterventionList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceIntervention in Elasticsearch
        verify(mockServiceInterventionSearchRepository, times(0)).save(serviceIntervention);
    }


    @Test
    @Transactional
    public void getAllServiceInterventions() throws Exception {
        // Initialize the database
        serviceInterventionRepository.saveAndFlush(serviceIntervention);

        // Get all the serviceInterventionList
        restServiceInterventionMockMvc.perform(get("/api/service-interventions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceIntervention.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getServiceIntervention() throws Exception {
        // Initialize the database
        serviceInterventionRepository.saveAndFlush(serviceIntervention);

        // Get the serviceIntervention
        restServiceInterventionMockMvc.perform(get("/api/service-interventions/{id}", serviceIntervention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceIntervention.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingServiceIntervention() throws Exception {
        // Get the serviceIntervention
        restServiceInterventionMockMvc.perform(get("/api/service-interventions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceIntervention() throws Exception {
        // Initialize the database
        serviceInterventionRepository.saveAndFlush(serviceIntervention);

        int databaseSizeBeforeUpdate = serviceInterventionRepository.findAll().size();

        // Update the serviceIntervention
        ServiceIntervention updatedServiceIntervention = serviceInterventionRepository.findById(serviceIntervention.getId()).get();
        // Disconnect from session so that the updates on updatedServiceIntervention are not directly saved in db
        em.detach(updatedServiceIntervention);
        updatedServiceIntervention
            .dateTime(UPDATED_DATE_TIME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION);
        ServiceInterventionDTO serviceInterventionDTO = serviceInterventionMapper.toDto(updatedServiceIntervention);

        restServiceInterventionMockMvc.perform(put("/api/service-interventions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceInterventionDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceIntervention in the database
        List<ServiceIntervention> serviceInterventionList = serviceInterventionRepository.findAll();
        assertThat(serviceInterventionList).hasSize(databaseSizeBeforeUpdate);
        ServiceIntervention testServiceIntervention = serviceInterventionList.get(serviceInterventionList.size() - 1);
        assertThat(testServiceIntervention.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testServiceIntervention.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testServiceIntervention.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ServiceIntervention in Elasticsearch
        verify(mockServiceInterventionSearchRepository, times(1)).save(testServiceIntervention);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceIntervention() throws Exception {
        int databaseSizeBeforeUpdate = serviceInterventionRepository.findAll().size();

        // Create the ServiceIntervention
        ServiceInterventionDTO serviceInterventionDTO = serviceInterventionMapper.toDto(serviceIntervention);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceInterventionMockMvc.perform(put("/api/service-interventions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceInterventionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceIntervention in the database
        List<ServiceIntervention> serviceInterventionList = serviceInterventionRepository.findAll();
        assertThat(serviceInterventionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceIntervention in Elasticsearch
        verify(mockServiceInterventionSearchRepository, times(0)).save(serviceIntervention);
    }

    @Test
    @Transactional
    public void deleteServiceIntervention() throws Exception {
        // Initialize the database
        serviceInterventionRepository.saveAndFlush(serviceIntervention);

        int databaseSizeBeforeDelete = serviceInterventionRepository.findAll().size();

        // Delete the serviceIntervention
        restServiceInterventionMockMvc.perform(delete("/api/service-interventions/{id}", serviceIntervention.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceIntervention> serviceInterventionList = serviceInterventionRepository.findAll();
        assertThat(serviceInterventionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceIntervention in Elasticsearch
        verify(mockServiceInterventionSearchRepository, times(1)).deleteById(serviceIntervention.getId());
    }

    @Test
    @Transactional
    public void searchServiceIntervention() throws Exception {
        // Initialize the database
        serviceInterventionRepository.saveAndFlush(serviceIntervention);
        when(mockServiceInterventionSearchRepository.search(queryStringQuery("id:" + serviceIntervention.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serviceIntervention), PageRequest.of(0, 1), 1));
        // Search the serviceIntervention
        restServiceInterventionMockMvc.perform(get("/api/_search/service-interventions?query=id:" + serviceIntervention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceIntervention.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
