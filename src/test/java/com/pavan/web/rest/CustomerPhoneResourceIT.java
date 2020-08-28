package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.CustomerPhone;
import com.pavan.repository.CustomerPhoneRepository;

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
 * Integration tests for the {@link CustomerPhoneResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPhoneResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private CustomerPhoneRepository customerPhoneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPhoneMockMvc;

    private CustomerPhone customerPhone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPhone createEntity(EntityManager em) {
        CustomerPhone customerPhone = new CustomerPhone()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .extension(DEFAULT_EXTENSION)
            .deleted(DEFAULT_DELETED);
        return customerPhone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPhone createUpdatedEntity(EntityManager em) {
        CustomerPhone customerPhone = new CustomerPhone()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .extension(UPDATED_EXTENSION)
            .deleted(UPDATED_DELETED);
        return customerPhone;
    }

    @BeforeEach
    public void initTest() {
        customerPhone = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPhone() throws Exception {
        int databaseSizeBeforeCreate = customerPhoneRepository.findAll().size();
        // Create the CustomerPhone
        restCustomerPhoneMockMvc.perform(post("/api/customer-phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPhone)))
            .andExpect(status().isCreated());

        // Validate the CustomerPhone in the database
        List<CustomerPhone> customerPhoneList = customerPhoneRepository.findAll();
        assertThat(customerPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPhone testCustomerPhone = customerPhoneList.get(customerPhoneList.size() - 1);
        assertThat(testCustomerPhone.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCustomerPhone.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testCustomerPhone.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createCustomerPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPhoneRepository.findAll().size();

        // Create the CustomerPhone with an existing ID
        customerPhone.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPhoneMockMvc.perform(post("/api/customer-phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPhone)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPhone in the database
        List<CustomerPhone> customerPhoneList = customerPhoneRepository.findAll();
        assertThat(customerPhoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerPhones() throws Exception {
        // Initialize the database
        customerPhoneRepository.saveAndFlush(customerPhone);

        // Get all the customerPhoneList
        restCustomerPhoneMockMvc.perform(get("/api/customer-phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerPhone() throws Exception {
        // Initialize the database
        customerPhoneRepository.saveAndFlush(customerPhone);

        // Get the customerPhone
        restCustomerPhoneMockMvc.perform(get("/api/customer-phones/{id}", customerPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPhone.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerPhone() throws Exception {
        // Get the customerPhone
        restCustomerPhoneMockMvc.perform(get("/api/customer-phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPhone() throws Exception {
        // Initialize the database
        customerPhoneRepository.saveAndFlush(customerPhone);

        int databaseSizeBeforeUpdate = customerPhoneRepository.findAll().size();

        // Update the customerPhone
        CustomerPhone updatedCustomerPhone = customerPhoneRepository.findById(customerPhone.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPhone are not directly saved in db
        em.detach(updatedCustomerPhone);
        updatedCustomerPhone
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .extension(UPDATED_EXTENSION)
            .deleted(UPDATED_DELETED);

        restCustomerPhoneMockMvc.perform(put("/api/customer-phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerPhone)))
            .andExpect(status().isOk());

        // Validate the CustomerPhone in the database
        List<CustomerPhone> customerPhoneList = customerPhoneRepository.findAll();
        assertThat(customerPhoneList).hasSize(databaseSizeBeforeUpdate);
        CustomerPhone testCustomerPhone = customerPhoneList.get(customerPhoneList.size() - 1);
        assertThat(testCustomerPhone.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustomerPhone.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testCustomerPhone.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPhone() throws Exception {
        int databaseSizeBeforeUpdate = customerPhoneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPhoneMockMvc.perform(put("/api/customer-phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPhone)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPhone in the database
        List<CustomerPhone> customerPhoneList = customerPhoneRepository.findAll();
        assertThat(customerPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPhone() throws Exception {
        // Initialize the database
        customerPhoneRepository.saveAndFlush(customerPhone);

        int databaseSizeBeforeDelete = customerPhoneRepository.findAll().size();

        // Delete the customerPhone
        restCustomerPhoneMockMvc.perform(delete("/api/customer-phones/{id}", customerPhone.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPhone> customerPhoneList = customerPhoneRepository.findAll();
        assertThat(customerPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
