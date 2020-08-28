package com.pavan.web.rest;

import com.pavan.domain.CustomerPhone;
import com.pavan.repository.CustomerPhoneRepository;
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
 * REST controller for managing {@link com.pavan.domain.CustomerPhone}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerPhoneResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPhoneResource.class);

    private static final String ENTITY_NAME = "customerPhone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPhoneRepository customerPhoneRepository;

    public CustomerPhoneResource(CustomerPhoneRepository customerPhoneRepository) {
        this.customerPhoneRepository = customerPhoneRepository;
    }

    /**
     * {@code POST  /customer-phones} : Create a new customerPhone.
     *
     * @param customerPhone the customerPhone to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPhone, or with status {@code 400 (Bad Request)} if the customerPhone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-phones")
    public ResponseEntity<CustomerPhone> createCustomerPhone(@RequestBody CustomerPhone customerPhone) throws URISyntaxException {
        log.debug("REST request to save CustomerPhone : {}", customerPhone);
        if (customerPhone.getId() != null) {
            throw new BadRequestAlertException("A new customerPhone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPhone result = customerPhoneRepository.save(customerPhone);
        return ResponseEntity.created(new URI("/api/customer-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-phones} : Updates an existing customerPhone.
     *
     * @param customerPhone the customerPhone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPhone,
     * or with status {@code 400 (Bad Request)} if the customerPhone is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPhone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-phones")
    public ResponseEntity<CustomerPhone> updateCustomerPhone(@RequestBody CustomerPhone customerPhone) throws URISyntaxException {
        log.debug("REST request to update CustomerPhone : {}", customerPhone);
        if (customerPhone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPhone result = customerPhoneRepository.save(customerPhone);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPhone.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-phones} : get all the customerPhones.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPhones in body.
     */
    @GetMapping("/customer-phones")
    public List<CustomerPhone> getAllCustomerPhones() {
        log.debug("REST request to get all CustomerPhones");
        return customerPhoneRepository.findAll();
    }

    /**
     * {@code GET  /customer-phones/:id} : get the "id" customerPhone.
     *
     * @param id the id of the customerPhone to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPhone, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-phones/{id}")
    public ResponseEntity<CustomerPhone> getCustomerPhone(@PathVariable Long id) {
        log.debug("REST request to get CustomerPhone : {}", id);
        Optional<CustomerPhone> customerPhone = customerPhoneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerPhone);
    }

    /**
     * {@code DELETE  /customer-phones/:id} : delete the "id" customerPhone.
     *
     * @param id the id of the customerPhone to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-phones/{id}")
    public ResponseEntity<Void> deleteCustomerPhone(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPhone : {}", id);
        customerPhoneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
