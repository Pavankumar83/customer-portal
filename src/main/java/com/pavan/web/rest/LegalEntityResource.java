package com.pavan.web.rest;

import com.pavan.domain.LegalEntity;
import com.pavan.repository.LegalEntityRepository;
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
 * REST controller for managing {@link com.pavan.domain.LegalEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LegalEntityResource {

    private final Logger log = LoggerFactory.getLogger(LegalEntityResource.class);

    private static final String ENTITY_NAME = "legalEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LegalEntityRepository legalEntityRepository;

    public LegalEntityResource(LegalEntityRepository legalEntityRepository) {
        this.legalEntityRepository = legalEntityRepository;
    }

    /**
     * {@code POST  /legal-entities} : Create a new legalEntity.
     *
     * @param legalEntity the legalEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new legalEntity, or with status {@code 400 (Bad Request)} if the legalEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/legal-entities")
    public ResponseEntity<LegalEntity> createLegalEntity(@RequestBody LegalEntity legalEntity) throws URISyntaxException {
        log.debug("REST request to save LegalEntity : {}", legalEntity);
        if (legalEntity.getId() != null) {
            throw new BadRequestAlertException("A new legalEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LegalEntity result = legalEntityRepository.save(legalEntity);
        return ResponseEntity.created(new URI("/api/legal-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /legal-entities} : Updates an existing legalEntity.
     *
     * @param legalEntity the legalEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legalEntity,
     * or with status {@code 400 (Bad Request)} if the legalEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the legalEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/legal-entities")
    public ResponseEntity<LegalEntity> updateLegalEntity(@RequestBody LegalEntity legalEntity) throws URISyntaxException {
        log.debug("REST request to update LegalEntity : {}", legalEntity);
        if (legalEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LegalEntity result = legalEntityRepository.save(legalEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, legalEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /legal-entities} : get all the legalEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of legalEntities in body.
     */
    @GetMapping("/legal-entities")
    public List<LegalEntity> getAllLegalEntities() {
        log.debug("REST request to get all LegalEntities");
        return legalEntityRepository.findAll();
    }

    /**
     * {@code GET  /legal-entities/:id} : get the "id" legalEntity.
     *
     * @param id the id of the legalEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the legalEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/legal-entities/{id}")
    public ResponseEntity<LegalEntity> getLegalEntity(@PathVariable Long id) {
        log.debug("REST request to get LegalEntity : {}", id);
        Optional<LegalEntity> legalEntity = legalEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(legalEntity);
    }

    /**
     * {@code DELETE  /legal-entities/:id} : delete the "id" legalEntity.
     *
     * @param id the id of the legalEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/legal-entities/{id}")
    public ResponseEntity<Void> deleteLegalEntity(@PathVariable Long id) {
        log.debug("REST request to delete LegalEntity : {}", id);
        legalEntityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
