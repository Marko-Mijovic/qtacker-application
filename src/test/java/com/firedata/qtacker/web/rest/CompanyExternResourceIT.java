package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.QtackerApplicationApp;
import com.firedata.qtacker.config.TestSecurityConfiguration;
import com.firedata.qtacker.domain.CompanyExtern;
import com.firedata.qtacker.repository.CompanyExternRepository;
import com.firedata.qtacker.repository.search.CompanyExternSearchRepository;
import com.firedata.qtacker.service.CompanyExternService;
import com.firedata.qtacker.service.dto.CompanyExternDTO;
import com.firedata.qtacker.service.mapper.CompanyExternMapper;

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
 * Integration tests for the {@link CompanyExternResource} REST controller.
 */
@SpringBootTest(classes = { QtackerApplicationApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyExternResourceIT {

    @Autowired
    private CompanyExternRepository companyExternRepository;

    @Autowired
    private CompanyExternMapper companyExternMapper;

    @Autowired
    private CompanyExternService companyExternService;

    /**
     * This repository is mocked in the com.firedata.qtacker.repository.search test package.
     *
     * @see com.firedata.qtacker.repository.search.CompanyExternSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompanyExternSearchRepository mockCompanyExternSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyExternMockMvc;

    private CompanyExtern companyExtern;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyExtern createEntity(EntityManager em) {
        CompanyExtern companyExtern = new CompanyExtern();
        return companyExtern;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyExtern createUpdatedEntity(EntityManager em) {
        CompanyExtern companyExtern = new CompanyExtern();
        return companyExtern;
    }

    @BeforeEach
    public void initTest() {
        companyExtern = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyExtern() throws Exception {
        int databaseSizeBeforeCreate = companyExternRepository.findAll().size();

        // Create the CompanyExtern
        CompanyExternDTO companyExternDTO = companyExternMapper.toDto(companyExtern);
        restCompanyExternMockMvc.perform(post("/api/company-externs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyExternDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyExtern in the database
        List<CompanyExtern> companyExternList = companyExternRepository.findAll();
        assertThat(companyExternList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyExtern testCompanyExtern = companyExternList.get(companyExternList.size() - 1);

        // Validate the CompanyExtern in Elasticsearch
        verify(mockCompanyExternSearchRepository, times(1)).save(testCompanyExtern);
    }

    @Test
    @Transactional
    public void createCompanyExternWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyExternRepository.findAll().size();

        // Create the CompanyExtern with an existing ID
        companyExtern.setId(1L);
        CompanyExternDTO companyExternDTO = companyExternMapper.toDto(companyExtern);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyExternMockMvc.perform(post("/api/company-externs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyExternDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyExtern in the database
        List<CompanyExtern> companyExternList = companyExternRepository.findAll();
        assertThat(companyExternList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompanyExtern in Elasticsearch
        verify(mockCompanyExternSearchRepository, times(0)).save(companyExtern);
    }


    @Test
    @Transactional
    public void getAllCompanyExterns() throws Exception {
        // Initialize the database
        companyExternRepository.saveAndFlush(companyExtern);

        // Get all the companyExternList
        restCompanyExternMockMvc.perform(get("/api/company-externs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyExtern.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCompanyExtern() throws Exception {
        // Initialize the database
        companyExternRepository.saveAndFlush(companyExtern);

        // Get the companyExtern
        restCompanyExternMockMvc.perform(get("/api/company-externs/{id}", companyExtern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyExtern.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyExtern() throws Exception {
        // Get the companyExtern
        restCompanyExternMockMvc.perform(get("/api/company-externs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyExtern() throws Exception {
        // Initialize the database
        companyExternRepository.saveAndFlush(companyExtern);

        int databaseSizeBeforeUpdate = companyExternRepository.findAll().size();

        // Update the companyExtern
        CompanyExtern updatedCompanyExtern = companyExternRepository.findById(companyExtern.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyExtern are not directly saved in db
        em.detach(updatedCompanyExtern);
        CompanyExternDTO companyExternDTO = companyExternMapper.toDto(updatedCompanyExtern);

        restCompanyExternMockMvc.perform(put("/api/company-externs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyExternDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyExtern in the database
        List<CompanyExtern> companyExternList = companyExternRepository.findAll();
        assertThat(companyExternList).hasSize(databaseSizeBeforeUpdate);
        CompanyExtern testCompanyExtern = companyExternList.get(companyExternList.size() - 1);

        // Validate the CompanyExtern in Elasticsearch
        verify(mockCompanyExternSearchRepository, times(1)).save(testCompanyExtern);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyExtern() throws Exception {
        int databaseSizeBeforeUpdate = companyExternRepository.findAll().size();

        // Create the CompanyExtern
        CompanyExternDTO companyExternDTO = companyExternMapper.toDto(companyExtern);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyExternMockMvc.perform(put("/api/company-externs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyExternDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyExtern in the database
        List<CompanyExtern> companyExternList = companyExternRepository.findAll();
        assertThat(companyExternList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompanyExtern in Elasticsearch
        verify(mockCompanyExternSearchRepository, times(0)).save(companyExtern);
    }

    @Test
    @Transactional
    public void deleteCompanyExtern() throws Exception {
        // Initialize the database
        companyExternRepository.saveAndFlush(companyExtern);

        int databaseSizeBeforeDelete = companyExternRepository.findAll().size();

        // Delete the companyExtern
        restCompanyExternMockMvc.perform(delete("/api/company-externs/{id}", companyExtern.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyExtern> companyExternList = companyExternRepository.findAll();
        assertThat(companyExternList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompanyExtern in Elasticsearch
        verify(mockCompanyExternSearchRepository, times(1)).deleteById(companyExtern.getId());
    }

    @Test
    @Transactional
    public void searchCompanyExtern() throws Exception {
        // Initialize the database
        companyExternRepository.saveAndFlush(companyExtern);
        when(mockCompanyExternSearchRepository.search(queryStringQuery("id:" + companyExtern.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(companyExtern), PageRequest.of(0, 1), 1));
        // Search the companyExtern
        restCompanyExternMockMvc.perform(get("/api/_search/company-externs?query=id:" + companyExtern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyExtern.getId().intValue())));
    }
}
