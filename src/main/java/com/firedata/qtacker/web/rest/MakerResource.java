package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.service.MakerService;
import com.firedata.qtacker.web.rest.errors.BadRequestAlertException;
import com.firedata.qtacker.service.dto.MakerDTO;

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
 * REST controller for managing {@link com.firedata.qtacker.domain.Maker}.
 */
@RestController
@RequestMapping("/api")
public class MakerResource {

    private final Logger log = LoggerFactory.getLogger(MakerResource.class);

    private static final String ENTITY_NAME = "maker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MakerService makerService;

    public MakerResource(MakerService makerService) {
        this.makerService = makerService;
    }

    /**
     * {@code POST  /makers} : Create a new maker.
     *
     * @param makerDTO the makerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new makerDTO, or with status {@code 400 (Bad Request)} if the maker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/makers")
    public ResponseEntity<MakerDTO> createMaker(@RequestBody MakerDTO makerDTO) throws URISyntaxException {
        log.debug("REST request to save Maker : {}", makerDTO);
        if (makerDTO.getId() != null) {
            throw new BadRequestAlertException("A new maker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MakerDTO result = makerService.save(makerDTO);
        return ResponseEntity.created(new URI("/api/makers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /makers} : Updates an existing maker.
     *
     * @param makerDTO the makerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated makerDTO,
     * or with status {@code 400 (Bad Request)} if the makerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the makerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/makers")
    public ResponseEntity<MakerDTO> updateMaker(@RequestBody MakerDTO makerDTO) throws URISyntaxException {
        log.debug("REST request to update Maker : {}", makerDTO);
        if (makerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MakerDTO result = makerService.save(makerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, makerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /makers} : get all the makers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of makers in body.
     */
    @GetMapping("/makers")
    public ResponseEntity<List<MakerDTO>> getAllMakers(Pageable pageable) {
        log.debug("REST request to get a page of Makers");
        Page<MakerDTO> page = makerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /makers/:id} : get the "id" maker.
     *
     * @param id the id of the makerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the makerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/makers/{id}")
    public ResponseEntity<MakerDTO> getMaker(@PathVariable Long id) {
        log.debug("REST request to get Maker : {}", id);
        Optional<MakerDTO> makerDTO = makerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(makerDTO);
    }

    /**
     * {@code DELETE  /makers/:id} : delete the "id" maker.
     *
     * @param id the id of the makerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/makers/{id}")
    public ResponseEntity<Void> deleteMaker(@PathVariable Long id) {
        log.debug("REST request to delete Maker : {}", id);
        makerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/makers?query=:query} : search for the maker corresponding
     * to the query.
     *
     * @param query the query of the maker search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/makers")
    public ResponseEntity<List<MakerDTO>> searchMakers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Makers for query {}", query);
        Page<MakerDTO> page = makerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
