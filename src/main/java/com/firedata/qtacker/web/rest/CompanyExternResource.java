package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.service.CompanyExternService;
import com.firedata.qtacker.web.rest.errors.BadRequestAlertException;
import com.firedata.qtacker.service.dto.CompanyExternDTO;

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
 * REST controller for managing {@link com.firedata.qtacker.domain.CompanyExtern}.
 */
@RestController
@RequestMapping("/api")
public class CompanyExternResource {

    private final Logger log = LoggerFactory.getLogger(CompanyExternResource.class);

    private static final String ENTITY_NAME = "companyExtern";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyExternService companyExternService;

    public CompanyExternResource(CompanyExternService companyExternService) {
        this.companyExternService = companyExternService;
    }

    /**
     * {@code POST  /company-externs} : Create a new companyExtern.
     *
     * @param companyExternDTO the companyExternDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyExternDTO, or with status {@code 400 (Bad Request)} if the companyExtern has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-externs")
    public ResponseEntity<CompanyExternDTO> createCompanyExtern(@RequestBody CompanyExternDTO companyExternDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyExtern : {}", companyExternDTO);
        if (companyExternDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyExtern cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyExternDTO result = companyExternService.save(companyExternDTO);
        return ResponseEntity.created(new URI("/api/company-externs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-externs} : Updates an existing companyExtern.
     *
     * @param companyExternDTO the companyExternDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyExternDTO,
     * or with status {@code 400 (Bad Request)} if the companyExternDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyExternDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-externs")
    public ResponseEntity<CompanyExternDTO> updateCompanyExtern(@RequestBody CompanyExternDTO companyExternDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyExtern : {}", companyExternDTO);
        if (companyExternDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyExternDTO result = companyExternService.save(companyExternDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyExternDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-externs} : get all the companyExterns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyExterns in body.
     */
    @GetMapping("/company-externs")
    public ResponseEntity<List<CompanyExternDTO>> getAllCompanyExterns(Pageable pageable) {
        log.debug("REST request to get a page of CompanyExterns");
        Page<CompanyExternDTO> page = companyExternService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-externs/:id} : get the "id" companyExtern.
     *
     * @param id the id of the companyExternDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyExternDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-externs/{id}")
    public ResponseEntity<CompanyExternDTO> getCompanyExtern(@PathVariable Long id) {
        log.debug("REST request to get CompanyExtern : {}", id);
        Optional<CompanyExternDTO> companyExternDTO = companyExternService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyExternDTO);
    }

    /**
     * {@code DELETE  /company-externs/:id} : delete the "id" companyExtern.
     *
     * @param id the id of the companyExternDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-externs/{id}")
    public ResponseEntity<Void> deleteCompanyExtern(@PathVariable Long id) {
        log.debug("REST request to delete CompanyExtern : {}", id);
        companyExternService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/company-externs?query=:query} : search for the companyExtern corresponding
     * to the query.
     *
     * @param query the query of the companyExtern search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/company-externs")
    public ResponseEntity<List<CompanyExternDTO>> searchCompanyExterns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompanyExterns for query {}", query);
        Page<CompanyExternDTO> page = companyExternService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
