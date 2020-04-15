package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.QtackerApplicationApp;
import com.firedata.qtacker.config.TestSecurityConfiguration;
import com.firedata.qtacker.domain.Maker;
import com.firedata.qtacker.repository.MakerRepository;
import com.firedata.qtacker.repository.search.MakerSearchRepository;
import com.firedata.qtacker.service.MakerService;
import com.firedata.qtacker.service.dto.MakerDTO;
import com.firedata.qtacker.service.mapper.MakerMapper;

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
 * Integration tests for the {@link MakerResource} REST controller.
 */
@SpringBootTest(classes = { QtackerApplicationApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MakerResourceIT {

    private static final String DEFAULT_MAKER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MAKER_NAME = "BBBBBBBBBB";

    @Autowired
    private MakerRepository makerRepository;

    @Autowired
    private MakerMapper makerMapper;

    @Autowired
    private MakerService makerService;

    /**
     * This repository is mocked in the com.firedata.qtacker.repository.search test package.
     *
     * @see com.firedata.qtacker.repository.search.MakerSearchRepositoryMockConfiguration
     */
    @Autowired
    private MakerSearchRepository mockMakerSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMakerMockMvc;

    private Maker maker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maker createEntity(EntityManager em) {
        Maker maker = new Maker()
            .makerName(DEFAULT_MAKER_NAME);
        return maker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maker createUpdatedEntity(EntityManager em) {
        Maker maker = new Maker()
            .makerName(UPDATED_MAKER_NAME);
        return maker;
    }

    @BeforeEach
    public void initTest() {
        maker = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaker() throws Exception {
        int databaseSizeBeforeCreate = makerRepository.findAll().size();

        // Create the Maker
        MakerDTO makerDTO = makerMapper.toDto(maker);
        restMakerMockMvc.perform(post("/api/makers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(makerDTO)))
            .andExpect(status().isCreated());

        // Validate the Maker in the database
        List<Maker> makerList = makerRepository.findAll();
        assertThat(makerList).hasSize(databaseSizeBeforeCreate + 1);
        Maker testMaker = makerList.get(makerList.size() - 1);
        assertThat(testMaker.getMakerName()).isEqualTo(DEFAULT_MAKER_NAME);

        // Validate the Maker in Elasticsearch
        verify(mockMakerSearchRepository, times(1)).save(testMaker);
    }

    @Test
    @Transactional
    public void createMakerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = makerRepository.findAll().size();

        // Create the Maker with an existing ID
        maker.setId(1L);
        MakerDTO makerDTO = makerMapper.toDto(maker);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMakerMockMvc.perform(post("/api/makers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(makerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Maker in the database
        List<Maker> makerList = makerRepository.findAll();
        assertThat(makerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Maker in Elasticsearch
        verify(mockMakerSearchRepository, times(0)).save(maker);
    }


    @Test
    @Transactional
    public void getAllMakers() throws Exception {
        // Initialize the database
        makerRepository.saveAndFlush(maker);

        // Get all the makerList
        restMakerMockMvc.perform(get("/api/makers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maker.getId().intValue())))
            .andExpect(jsonPath("$.[*].makerName").value(hasItem(DEFAULT_MAKER_NAME)));
    }
    
    @Test
    @Transactional
    public void getMaker() throws Exception {
        // Initialize the database
        makerRepository.saveAndFlush(maker);

        // Get the maker
        restMakerMockMvc.perform(get("/api/makers/{id}", maker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maker.getId().intValue()))
            .andExpect(jsonPath("$.makerName").value(DEFAULT_MAKER_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingMaker() throws Exception {
        // Get the maker
        restMakerMockMvc.perform(get("/api/makers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaker() throws Exception {
        // Initialize the database
        makerRepository.saveAndFlush(maker);

        int databaseSizeBeforeUpdate = makerRepository.findAll().size();

        // Update the maker
        Maker updatedMaker = makerRepository.findById(maker.getId()).get();
        // Disconnect from session so that the updates on updatedMaker are not directly saved in db
        em.detach(updatedMaker);
        updatedMaker
            .makerName(UPDATED_MAKER_NAME);
        MakerDTO makerDTO = makerMapper.toDto(updatedMaker);

        restMakerMockMvc.perform(put("/api/makers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(makerDTO)))
            .andExpect(status().isOk());

        // Validate the Maker in the database
        List<Maker> makerList = makerRepository.findAll();
        assertThat(makerList).hasSize(databaseSizeBeforeUpdate);
        Maker testMaker = makerList.get(makerList.size() - 1);
        assertThat(testMaker.getMakerName()).isEqualTo(UPDATED_MAKER_NAME);

        // Validate the Maker in Elasticsearch
        verify(mockMakerSearchRepository, times(1)).save(testMaker);
    }

    @Test
    @Transactional
    public void updateNonExistingMaker() throws Exception {
        int databaseSizeBeforeUpdate = makerRepository.findAll().size();

        // Create the Maker
        MakerDTO makerDTO = makerMapper.toDto(maker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMakerMockMvc.perform(put("/api/makers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(makerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Maker in the database
        List<Maker> makerList = makerRepository.findAll();
        assertThat(makerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Maker in Elasticsearch
        verify(mockMakerSearchRepository, times(0)).save(maker);
    }

    @Test
    @Transactional
    public void deleteMaker() throws Exception {
        // Initialize the database
        makerRepository.saveAndFlush(maker);

        int databaseSizeBeforeDelete = makerRepository.findAll().size();

        // Delete the maker
        restMakerMockMvc.perform(delete("/api/makers/{id}", maker.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Maker> makerList = makerRepository.findAll();
        assertThat(makerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Maker in Elasticsearch
        verify(mockMakerSearchRepository, times(1)).deleteById(maker.getId());
    }

    @Test
    @Transactional
    public void searchMaker() throws Exception {
        // Initialize the database
        makerRepository.saveAndFlush(maker);
        when(mockMakerSearchRepository.search(queryStringQuery("id:" + maker.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(maker), PageRequest.of(0, 1), 1));
        // Search the maker
        restMakerMockMvc.perform(get("/api/_search/makers?query=id:" + maker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maker.getId().intValue())))
            .andExpect(jsonPath("$.[*].makerName").value(hasItem(DEFAULT_MAKER_NAME)));
    }
}
