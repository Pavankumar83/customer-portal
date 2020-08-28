package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.CustomerEmail;
import com.pavan.repository.CustomerEmailRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerEmailResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerEmailResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private CustomerEmailRepository customerEmailRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerEmailMockMvc;

    private CustomerEmail customerEmail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerEmail createEntity(EntityManager em) {
        CustomerEmail customerEmail = new CustomerEmail()
            .email(DEFAULT_EMAIL)
            .deleted(DEFAULT_DELETED);
        return customerEmail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerEmail createUpdatedEntity(EntityManager em) {
        CustomerEmail customerEmail = new CustomerEmail()
            .email(UPDATED_EMAIL)
            .deleted(UPDATED_DELETED);
        return customerEmail;
    }

    @BeforeEach
    public void initTest() {
        customerEmail = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerEmail() throws Exception {
        int databaseSizeBeforeCreate = customerEmailRepository.findAll().size();
        // Create the CustomerEmail
        restCustomerEmailMockMvc.perform(post("/api/customer-emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerEmail)))
            .andExpect(status().isCreated());

        // Validate the CustomerEmail in the database
        List<CustomerEmail> customerEmailList = customerEmailRepository.findAll();
        assertThat(customerEmailList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerEmail testCustomerEmail = customerEmailList.get(customerEmailList.size() - 1);
        assertThat(testCustomerEmail.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerEmail.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createCustomerEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerEmailRepository.findAll().size();

        // Create the CustomerEmail with an existing ID
        customerEmail.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerEmailMockMvc.perform(post("/api/customer-emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerEmail)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerEmail in the database
        List<CustomerEmail> customerEmailList = customerEmailRepository.findAll();
        assertThat(customerEmailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerEmails() throws Exception {
        // Initialize the database
        customerEmailRepository.saveAndFlush(customerEmail);

        // Get all the customerEmailList
        restCustomerEmailMockMvc.perform(get("/api/customer-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerEmail.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerEmail() throws Exception {
        // Initialize the database
        customerEmailRepository.saveAndFlush(customerEmail);

        // Get the customerEmail
        restCustomerEmailMockMvc.perform(get("/api/customer-emails/{id}", customerEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerEmail.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerEmail() throws Exception {
        // Get the customerEmail
        restCustomerEmailMockMvc.perform(get("/api/customer-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerEmail() throws Exception {
        // Initialize the database
        customerEmailRepository.saveAndFlush(customerEmail);

        int databaseSizeBeforeUpdate = customerEmailRepository.findAll().size();

        // Update the customerEmail
        CustomerEmail updatedCustomerEmail = customerEmailRepository.findById(customerEmail.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerEmail are not directly saved in db
        em.detach(updatedCustomerEmail);
        updatedCustomerEmail
            .email(UPDATED_EMAIL)
            .deleted(UPDATED_DELETED);

        restCustomerEmailMockMvc.perform(put("/api/customer-emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerEmail)))
            .andExpect(status().isOk());

        // Validate the CustomerEmail in the database
        List<CustomerEmail> customerEmailList = customerEmailRepository.findAll();
        assertThat(customerEmailList).hasSize(databaseSizeBeforeUpdate);
        CustomerEmail testCustomerEmail = customerEmailList.get(customerEmailList.size() - 1);
        assertThat(testCustomerEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerEmail.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerEmail() throws Exception {
        int databaseSizeBeforeUpdate = customerEmailRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerEmailMockMvc.perform(put("/api/customer-emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerEmail)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerEmail in the database
        List<CustomerEmail> customerEmailList = customerEmailRepository.findAll();
        assertThat(customerEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerEmail() throws Exception {
        // Initialize the database
        customerEmailRepository.saveAndFlush(customerEmail);

        int databaseSizeBeforeDelete = customerEmailRepository.findAll().size();

        // Delete the customerEmail
        restCustomerEmailMockMvc.perform(delete("/api/customer-emails/{id}", customerEmail.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerEmail> customerEmailList = customerEmailRepository.findAll();
        assertThat(customerEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
