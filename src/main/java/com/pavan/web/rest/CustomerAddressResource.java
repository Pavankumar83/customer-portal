package com.pavan.web.rest;

import com.pavan.domain.CustomerAddress;
import com.pavan.repository.CustomerAddressRepository;
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
 * REST controller for managing {@link com.pavan.domain.CustomerAddress}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerAddressResource {

    private final Logger log = LoggerFactory.getLogger(CustomerAddressResource.class);

    private static final String ENTITY_NAME = "customerAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressResource(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    /**
     * {@code POST  /customer-addresses} : Create a new customerAddress.
     *
     * @param customerAddress the customerAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerAddress, or with status {@code 400 (Bad Request)} if the customerAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-addresses")
    public ResponseEntity<CustomerAddress> createCustomerAddress(@RequestBody CustomerAddress customerAddress) throws URISyntaxException {
        log.debug("REST request to save CustomerAddress : {}", customerAddress);
        if (customerAddress.getId() != null) {
            throw new BadRequestAlertException("A new customerAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerAddress result = customerAddressRepository.save(customerAddress);
        return ResponseEntity.created(new URI("/api/customer-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-addresses} : Updates an existing customerAddress.
     *
     * @param customerAddress the customerAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerAddress,
     * or with status {@code 400 (Bad Request)} if the customerAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-addresses")
    public ResponseEntity<CustomerAddress> updateCustomerAddress(@RequestBody CustomerAddress customerAddress) throws URISyntaxException {
        log.debug("REST request to update CustomerAddress : {}", customerAddress);
        if (customerAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerAddress result = customerAddressRepository.save(customerAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-addresses} : get all the customerAddresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerAddresses in body.
     */
    @GetMapping("/customer-addresses")
    public List<CustomerAddress> getAllCustomerAddresses() {
        log.debug("REST request to get all CustomerAddresses");
        return customerAddressRepository.findAll();
    }

    /**
     * {@code GET  /customer-addresses/:id} : get the "id" customerAddress.
     *
     * @param id the id of the customerAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-addresses/{id}")
    public ResponseEntity<CustomerAddress> getCustomerAddress(@PathVariable Long id) {
        log.debug("REST request to get CustomerAddress : {}", id);
        Optional<CustomerAddress> customerAddress = customerAddressRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerAddress);
    }

    /**
     * {@code DELETE  /customer-addresses/:id} : delete the "id" customerAddress.
     *
     * @param id the id of the customerAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-addresses/{id}")
    public ResponseEntity<Void> deleteCustomerAddress(@PathVariable Long id) {
        log.debug("REST request to delete CustomerAddress : {}", id);
        customerAddressRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
