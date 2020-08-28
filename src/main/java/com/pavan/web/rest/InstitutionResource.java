package com.pavan.web.rest;

import com.pavan.domain.Institution;
import com.pavan.repository.InstitutionRepository;
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
 * REST controller for managing {@link com.pavan.domain.Institution}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InstitutionResource {

    private final Logger log = LoggerFactory.getLogger(InstitutionResource.class);

    private static final String ENTITY_NAME = "institution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstitutionRepository institutionRepository;

    public InstitutionResource(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    /**
     * {@code POST  /institutions} : Create a new institution.
     *
     * @param institution the institution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new institution, or with status {@code 400 (Bad Request)} if the institution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/institutions")
    public ResponseEntity<Institution> createInstitution(@RequestBody Institution institution) throws URISyntaxException {
        log.debug("REST request to save Institution : {}", institution);
        if (institution.getId() != null) {
            throw new BadRequestAlertException("A new institution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Institution result = institutionRepository.save(institution);
        return ResponseEntity.created(new URI("/api/institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /institutions} : Updates an existing institution.
     *
     * @param institution the institution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institution,
     * or with status {@code 400 (Bad Request)} if the institution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the institution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/institutions")
    public ResponseEntity<Institution> updateInstitution(@RequestBody Institution institution) throws URISyntaxException {
        log.debug("REST request to update Institution : {}", institution);
        if (institution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Institution result = institutionRepository.save(institution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, institution.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /institutions} : get all the institutions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of institutions in body.
     */
    @GetMapping("/institutions")
    public List<Institution> getAllInstitutions() {
        log.debug("REST request to get all Institutions");
        return institutionRepository.findAll();
    }

    /**
     * {@code GET  /institutions/:id} : get the "id" institution.
     *
     * @param id the id of the institution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the institution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/institutions/{id}")
    public ResponseEntity<Institution> getInstitution(@PathVariable Long id) {
        log.debug("REST request to get Institution : {}", id);
        Optional<Institution> institution = institutionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(institution);
    }

    /**
     * {@code DELETE  /institutions/:id} : delete the "id" institution.
     *
     * @param id the id of the institution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/institutions/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        log.debug("REST request to delete Institution : {}", id);
        institutionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
