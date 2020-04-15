package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.service.ModelService;
import com.firedata.qtacker.web.rest.errors.BadRequestAlertException;
import com.firedata.qtacker.service.dto.ModelDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.firedata.qtacker.domain.Model}.
 */
@RestController
@RequestMapping("/api")
public class ModelResource {

    private final Logger log = LoggerFactory.getLogger(ModelResource.class);

    private static final String ENTITY_NAME = "model";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModelService modelService;

    public ModelResource(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * {@code POST  /models} : Create a new model.
     *
     * @param modelDTO the modelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelDTO, or with status {@code 400 (Bad Request)} if the model has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/models")
    public ResponseEntity<ModelDTO> createModel(@RequestBody ModelDTO modelDTO) throws URISyntaxException {
        log.debug("REST request to save Model : {}", modelDTO);
        if (modelDTO.getId() != null) {
            throw new BadRequestAlertException("A new model cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModelDTO result = modelService.save(modelDTO);
        return ResponseEntity.created(new URI("/api/models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /models} : Updates an existing model.
     *
     * @param modelDTO the modelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelDTO,
     * or with status {@code 400 (Bad Request)} if the modelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/models")
    public ResponseEntity<ModelDTO> updateModel(@RequestBody ModelDTO modelDTO) throws URISyntaxException {
        log.debug("REST request to update Model : {}", modelDTO);
        if (modelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModelDTO result = modelService.save(modelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /models} : get all the models.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of models in body.
     */
    @GetMapping("/models")
    public ResponseEntity<List<ModelDTO>> getAllModels(Pageable pageable) {
        log.debug("REST request to get a page of Models");
        Page<ModelDTO> page = modelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /models/:id} : get the "id" model.
     *
     * @param id the id of the modelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/models/{id}")
    public ResponseEntity<ModelDTO> getModel(@PathVariable Long id) {
        log.debug("REST request to get Model : {}", id);
        Optional<ModelDTO> modelDTO = modelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelDTO);
    }

    /**
     * {@code DELETE  /models/:id} : delete the "id" model.
     *
     * @param id the id of the modelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/models/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        log.debug("REST request to delete Model : {}", id);
        modelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/models?query=:query} : search for the model corresponding
     * to the query.
     *
     * @param query the query of the model search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/models")
    public ResponseEntity<List<ModelDTO>> searchModels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Models for query {}", query);
        Page<ModelDTO> page = modelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
