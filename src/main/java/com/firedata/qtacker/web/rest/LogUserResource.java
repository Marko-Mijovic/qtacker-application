package com.firedata.qtacker.web.rest;

import com.firedata.qtacker.service.LogUserService;
import com.firedata.qtacker.web.rest.errors.BadRequestAlertException;
import com.firedata.qtacker.service.dto.LogUserDTO;

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
 * REST controller for managing {@link com.firedata.qtacker.domain.LogUser}.
 */
@RestController
@RequestMapping("/api")
public class LogUserResource {

    private final Logger log = LoggerFactory.getLogger(LogUserResource.class);

    private static final String ENTITY_NAME = "logUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogUserService logUserService;

    public LogUserResource(LogUserService logUserService) {
        this.logUserService = logUserService;
    }

    /**
     * {@code POST  /log-users} : Create a new logUser.
     *
     * @param logUserDTO the logUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logUserDTO, or with status {@code 400 (Bad Request)} if the logUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/log-users")
    public ResponseEntity<LogUserDTO> createLogUser(@RequestBody LogUserDTO logUserDTO) throws URISyntaxException {
        log.debug("REST request to save LogUser : {}", logUserDTO);
        if (logUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new logUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogUserDTO result = logUserService.save(logUserDTO);
        return ResponseEntity.created(new URI("/api/log-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /log-users} : Updates an existing logUser.
     *
     * @param logUserDTO the logUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logUserDTO,
     * or with status {@code 400 (Bad Request)} if the logUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/log-users")
    public ResponseEntity<LogUserDTO> updateLogUser(@RequestBody LogUserDTO logUserDTO) throws URISyntaxException {
        log.debug("REST request to update LogUser : {}", logUserDTO);
        if (logUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogUserDTO result = logUserService.save(logUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /log-users} : get all the logUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logUsers in body.
     */
    @GetMapping("/log-users")
    public ResponseEntity<List<LogUserDTO>> getAllLogUsers(Pageable pageable) {
        log.debug("REST request to get a page of LogUsers");
        Page<LogUserDTO> page = logUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /log-users/:id} : get the "id" logUser.
     *
     * @param id the id of the logUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-users/{id}")
    public ResponseEntity<LogUserDTO> getLogUser(@PathVariable Long id) {
        log.debug("REST request to get LogUser : {}", id);
        Optional<LogUserDTO> logUserDTO = logUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logUserDTO);
    }

    /**
     * {@code DELETE  /log-users/:id} : delete the "id" logUser.
     *
     * @param id the id of the logUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/log-users/{id}")
    public ResponseEntity<Void> deleteLogUser(@PathVariable Long id) {
        log.debug("REST request to delete LogUser : {}", id);
        logUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/log-users?query=:query} : search for the logUser corresponding
     * to the query.
     *
     * @param query the query of the logUser search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/log-users")
    public ResponseEntity<List<LogUserDTO>> searchLogUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LogUsers for query {}", query);
        Page<LogUserDTO> page = logUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
