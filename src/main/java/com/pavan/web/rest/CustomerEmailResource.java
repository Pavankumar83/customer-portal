package com.pavan.web.rest;

import com.pavan.domain.CustomerEmail;
import com.pavan.repository.CustomerEmailRepository;
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
 * REST controller for managing {@link com.pavan.domain.CustomerEmail}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerEmailResource {

    private final Logger log = LoggerFactory.getLogger(CustomerEmailResource.class);

    private static final String ENTITY_NAME = "customerEmail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerEmailRepository customerEmailRepository;

    public CustomerEmailResource(CustomerEmailRepository customerEmailRepository) {
        this.customerEmailRepository = customerEmailRepository;
    }

    /**
     * {@code POST  /customer-emails} : Create a new customerEmail.
     *
     * @param customerEmail the customerEmail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerEmail, or with status {@code 400 (Bad Request)} if the customerEmail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-emails")
    public ResponseEntity<CustomerEmail> createCustomerEmail(@RequestBody CustomerEmail customerEmail) throws URISyntaxException {
        log.debug("REST request to save CustomerEmail : {}", customerEmail);
        if (customerEmail.getId() != null) {
            throw new BadRequestAlertException("A new customerEmail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerEmail result = customerEmailRepository.save(customerEmail);
        return ResponseEntity.created(new URI("/api/customer-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-emails} : Updates an existing customerEmail.
     *
     * @param customerEmail the customerEmail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerEmail,
     * or with status {@code 400 (Bad Request)} if the customerEmail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerEmail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-emails")
    public ResponseEntity<CustomerEmail> updateCustomerEmail(@RequestBody CustomerEmail customerEmail) throws URISyntaxException {
        log.debug("REST request to update CustomerEmail : {}", customerEmail);
        if (customerEmail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerEmail result = customerEmailRepository.save(customerEmail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerEmail.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-emails} : get all the customerEmails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerEmails in body.
     */
    @GetMapping("/customer-emails")
    public List<CustomerEmail> getAllCustomerEmails() {
        log.debug("REST request to get all CustomerEmails");
        return customerEmailRepository.findAll();
    }

    /**
     * {@code GET  /customer-emails/:id} : get the "id" customerEmail.
     *
     * @param id the id of the customerEmail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerEmail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-emails/{id}")
    public ResponseEntity<CustomerEmail> getCustomerEmail(@PathVariable Long id) {
        log.debug("REST request to get CustomerEmail : {}", id);
        Optional<CustomerEmail> customerEmail = customerEmailRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerEmail);
    }

    /**
     * {@code DELETE  /customer-emails/:id} : delete the "id" customerEmail.
     *
     * @param id the id of the customerEmail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-emails/{id}")
    public ResponseEntity<Void> deleteCustomerEmail(@PathVariable Long id) {
        log.debug("REST request to delete CustomerEmail : {}", id);
        customerEmailRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
