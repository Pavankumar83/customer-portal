package com.pavan.web.rest;

import com.pavan.domain.AvailableTransaction;
import com.pavan.repository.AvailableTransactionRepository;
import com.pavan.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pavan.domain.AvailableTransaction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AvailableTransactionResource {

    private final Logger log = LoggerFactory.getLogger(AvailableTransactionResource.class);

    private static final String ENTITY_NAME = "availableTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvailableTransactionRepository availableTransactionRepository;

    public AvailableTransactionResource(AvailableTransactionRepository availableTransactionRepository) {
        this.availableTransactionRepository = availableTransactionRepository;
    }

    /**
     * {@code POST  /available-transactions} : Create a new availableTransaction.
     *
     * @param availableTransaction the availableTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new availableTransaction, or with status {@code 400 (Bad Request)} if the availableTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/available-transactions")
    public ResponseEntity<AvailableTransaction> createAvailableTransaction(@Valid @RequestBody AvailableTransaction availableTransaction) throws URISyntaxException {
        log.debug("REST request to save AvailableTransaction : {}", availableTransaction);
        if (availableTransaction.getId() != null) {
            throw new BadRequestAlertException("A new availableTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvailableTransaction result = availableTransactionRepository.save(availableTransaction);
        return ResponseEntity.created(new URI("/api/available-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /available-transactions} : Updates an existing availableTransaction.
     *
     * @param availableTransaction the availableTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated availableTransaction,
     * or with status {@code 400 (Bad Request)} if the availableTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the availableTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/available-transactions")
    public ResponseEntity<AvailableTransaction> updateAvailableTransaction(@Valid @RequestBody AvailableTransaction availableTransaction) throws URISyntaxException {
        log.debug("REST request to update AvailableTransaction : {}", availableTransaction);
        if (availableTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvailableTransaction result = availableTransactionRepository.save(availableTransaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, availableTransaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /available-transactions} : get all the availableTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of availableTransactions in body.
     */
    @GetMapping("/available-transactions")
    public List<AvailableTransaction> getAllAvailableTransactions() {
        log.debug("REST request to get all AvailableTransactions");
        return availableTransactionRepository.findAll();
    }

    /**
     * {@code GET  /available-transactions/:id} : get the "id" availableTransaction.
     *
     * @param id the id of the availableTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the availableTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/available-transactions/{id}")
    public ResponseEntity<AvailableTransaction> getAvailableTransaction(@PathVariable Long id) {
        log.debug("REST request to get AvailableTransaction : {}", id);
        Optional<AvailableTransaction> availableTransaction = availableTransactionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(availableTransaction);
    }

    /**
     * {@code DELETE  /available-transactions/:id} : delete the "id" availableTransaction.
     *
     * @param id the id of the availableTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/available-transactions/{id}")
    public ResponseEntity<Void> deleteAvailableTransaction(@PathVariable Long id) {
        log.debug("REST request to delete AvailableTransaction : {}", id);
        availableTransactionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
