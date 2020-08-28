package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.CustomerAddress;
import com.pavan.repository.CustomerAddressRepository;

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
 * Integration tests for the {@link CustomerAddressResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerAddressResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TOWN = "AAAAAAAAAA";
    private static final String UPDATED_TOWN = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerAddressMockMvc;

    private CustomerAddress customerAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerAddress createEntity(EntityManager em) {
        CustomerAddress customerAddress = new CustomerAddress()
            .address(DEFAULT_ADDRESS)
            .town(DEFAULT_TOWN)
            .city(DEFAULT_CITY)
            .zipCode(DEFAULT_ZIP_CODE)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .deleted(DEFAULT_DELETED);
        return customerAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerAddress createUpdatedEntity(EntityManager em) {
        CustomerAddress customerAddress = new CustomerAddress()
            .address(UPDATED_ADDRESS)
            .town(UPDATED_TOWN)
            .city(UPDATED_CITY)
            .zipCode(UPDATED_ZIP_CODE)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .deleted(UPDATED_DELETED);
        return customerAddress;
    }

    @BeforeEach
    public void initTest() {
        customerAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerAddress() throws Exception {
        int databaseSizeBeforeCreate = customerAddressRepository.findAll().size();
        // Create the CustomerAddress
        restCustomerAddressMockMvc.perform(post("/api/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerAddress)))
            .andExpect(status().isCreated());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerAddress testCustomerAddress = customerAddressList.get(customerAddressList.size() - 1);
        assertThat(testCustomerAddress.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCustomerAddress.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testCustomerAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomerAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testCustomerAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCustomerAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCustomerAddress.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createCustomerAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerAddressRepository.findAll().size();

        // Create the CustomerAddress with an existing ID
        customerAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerAddressMockMvc.perform(post("/api/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerAddress)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerAddresses() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);

        // Get all the customerAddressList
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);

        // Get the customerAddress
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses/{id}", customerAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerAddress.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerAddress() throws Exception {
        // Get the customerAddress
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);

        int databaseSizeBeforeUpdate = customerAddressRepository.findAll().size();

        // Update the customerAddress
        CustomerAddress updatedCustomerAddress = customerAddressRepository.findById(customerAddress.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerAddress are not directly saved in db
        em.detach(updatedCustomerAddress);
        updatedCustomerAddress
            .address(UPDATED_ADDRESS)
            .town(UPDATED_TOWN)
            .city(UPDATED_CITY)
            .zipCode(UPDATED_ZIP_CODE)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .deleted(UPDATED_DELETED);

        restCustomerAddressMockMvc.perform(put("/api/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerAddress)))
            .andExpect(status().isOk());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeUpdate);
        CustomerAddress testCustomerAddress = customerAddressList.get(customerAddressList.size() - 1);
        assertThat(testCustomerAddress.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCustomerAddress.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testCustomerAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomerAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCustomerAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustomerAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCustomerAddress.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerAddress() throws Exception {
        int databaseSizeBeforeUpdate = customerAddressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerAddressMockMvc.perform(put("/api/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerAddress)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);

        int databaseSizeBeforeDelete = customerAddressRepository.findAll().size();

        // Delete the customerAddress
        restCustomerAddressMockMvc.perform(delete("/api/customer-addresses/{id}", customerAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
