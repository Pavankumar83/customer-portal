package com.pavan.web.rest;

import com.pavan.domain.NonWorkingDay;
import com.pavan.repository.NonWorkingDayRepository;
import com.pavan.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pavan.domain.NonWorkingDay}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NonWorkingDayResource {

    private final Logger log = LoggerFactory.getLogger(NonWorkingDayResource.class);

    private static final String ENTITY_NAME = "nonWorkingDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NonWorkingDayRepository nonWorkingDayRepository;

    public NonWorkingDayResource(NonWorkingDayRepository nonWorkingDayRepository) {
        this.nonWorkingDayRepository = nonWorkingDayRepository;
    }

    /**
     * {@code POST  /non-working-days} : Create a new nonWorkingDay.
     *
     * @param nonWorkingDay the nonWorkingDay to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nonWorkingDay, or with status {@code 400 (Bad Request)} if the nonWorkingDay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/non-working-days")
    public ResponseEntity<NonWorkingDay> createNonWorkingDay(@RequestBody NonWorkingDay nonWorkingDay) throws URISyntaxException {
        log.debug("REST request to save NonWorkingDay : {}", nonWorkingDay);
        if (nonWorkingDay.getId() != null) {
            throw new BadRequestAlertException("A new nonWorkingDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NonWorkingDay result = nonWorkingDayRepository.save(nonWorkingDay);
        return ResponseEntity.created(new URI("/api/non-working-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /non-working-days} : Updates an existing nonWorkingDay.
     *
     * @param nonWorkingDay the nonWorkingDay to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nonWorkingDay,
     * or with status {@code 400 (Bad Request)} if the nonWorkingDay is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nonWorkingDay couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/non-working-days")
    public ResponseEntity<NonWorkingDay> updateNonWorkingDay(@RequestBody NonWorkingDay nonWorkingDay) throws URISyntaxException {
        log.debug("REST request to update NonWorkingDay : {}", nonWorkingDay);
        if (nonWorkingDay.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NonWorkingDay result = nonWorkingDayRepository.save(nonWorkingDay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nonWorkingDay.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /non-working-days} : get all the nonWorkingDays.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nonWorkingDays in body.
     */
    @GetMapping("/non-working-days")
    public List<NonWorkingDay> getAllNonWorkingDays() {
        log.debug("REST request to get all NonWorkingDays");
        return nonWorkingDayRepository.findAll();
    }

    /**
     * {@code GET  /non-working-days/:id} : get the "id" nonWorkingDay.
     *
     * @param id the id of the nonWorkingDay to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nonWorkingDay, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/non-working-days/{id}")
    public ResponseEntity<NonWorkingDay> getNonWorkingDay(@PathVariable Long id) {
        log.debug("REST request to get NonWorkingDay : {}", id);
        Optional<NonWorkingDay> nonWorkingDay = nonWorkingDayRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nonWorkingDay);
    }

    /**
     * {@code DELETE  /non-working-days/:id} : delete the "id" nonWorkingDay.
     *
     * @param id the id of the nonWorkingDay to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/non-working-days/{id}")
    public ResponseEntity<Void> deleteNonWorkingDay(@PathVariable Long id) {
        log.debug("REST request to delete NonWorkingDay : {}", id);
        nonWorkingDayRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
