package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.service.ServiceInterventionService;
import com.firedata.qtacker.web.rest.errors.BadRequestAlertException;
import com.firedata.qtacker.service.dto.ServiceInterventionDTO;

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
 * REST controller for managing {@link com.firedata.qtacker.domain.ServiceIntervention}.
 */
@RestController
@RequestMapping("/api")
public class ServiceInterventionResource {

    private final Logger log = LoggerFactory.getLogger(ServiceInterventionResource.class);

    private static final String ENTITY_NAME = "serviceIntervention";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceInterventionService serviceInterventionService;

    public ServiceInterventionResource(ServiceInterventionService serviceInterventionService) {
        this.serviceInterventionService = serviceInterventionService;
    }

    /**
     * {@code POST  /service-interventions} : Create a new serviceIntervention.
     *
     * @param serviceInterventionDTO the serviceInterventionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceInterventionDTO, or with status {@code 400 (Bad Request)} if the serviceIntervention has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-interventions")
    public ResponseEntity<ServiceInterventionDTO> createServiceIntervention(@RequestBody ServiceInterventionDTO serviceInterventionDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceIntervention : {}", serviceInterventionDTO);
        if (serviceInterventionDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceIntervention cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceInterventionDTO result = serviceInterventionService.save(serviceInterventionDTO);
        return ResponseEntity.created(new URI("/api/service-interventions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-interventions} : Updates an existing serviceIntervention.
     *
     * @param serviceInterventionDTO the serviceInterventionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceInterventionDTO,
     * or with status {@code 400 (Bad Request)} if the serviceInterventionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceInterventionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-interventions")
    public ResponseEntity<ServiceInterventionDTO> updateServiceIntervention(@RequestBody ServiceInterventionDTO serviceInterventionDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceIntervention : {}", serviceInterventionDTO);
        if (serviceInterventionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceInterventionDTO result = serviceInterventionService.save(serviceInterventionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceInterventionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-interventions} : get all the serviceInterventions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceInterventions in body.
     */
    @GetMapping("/service-interventions")
    public ResponseEntity<List<ServiceInterventionDTO>> getAllServiceInterventions(Pageable pageable) {
        log.debug("REST request to get a page of ServiceInterventions");
        Page<ServiceInterventionDTO> page = serviceInterventionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-interventions/:id} : get the "id" serviceIntervention.
     *
     * @param id the id of the serviceInterventionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceInterventionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-interventions/{id}")
    public ResponseEntity<ServiceInterventionDTO> getServiceIntervention(@PathVariable Long id) {
        log.debug("REST request to get ServiceIntervention : {}", id);
        Optional<ServiceInterventionDTO> serviceInterventionDTO = serviceInterventionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceInterventionDTO);
    }

    /**
     * {@code DELETE  /service-interventions/:id} : delete the "id" serviceIntervention.
     *
     * @param id the id of the serviceInterventionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-interventions/{id}")
    public ResponseEntity<Void> deleteServiceIntervention(@PathVariable Long id) {
        log.debug("REST request to delete ServiceIntervention : {}", id);
        serviceInterventionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/service-interventions?query=:query} : search for the serviceIntervention corresponding
     * to the query.
     *
     * @param query the query of the serviceIntervention search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/service-interventions")
    public ResponseEntity<List<ServiceInterventionDTO>> searchServiceInterventions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ServiceInterventions for query {}", query);
        Page<ServiceInterventionDTO> page = serviceInterventionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
