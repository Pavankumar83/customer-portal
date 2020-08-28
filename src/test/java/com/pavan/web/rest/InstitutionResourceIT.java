package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.Institution;
import com.pavan.repository.InstitutionRepository;

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
 * Integration tests for the {@link InstitutionResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstitutionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRADE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstitutionMockMvc;

    private Institution institution;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institution createEntity(EntityManager em) {
        Institution institution = new Institution()
            .name(DEFAULT_NAME)
            .tradeName(DEFAULT_TRADE_NAME)
            .taxNumber(DEFAULT_TAX_NUMBER)
            .url(DEFAULT_URL)
            .deleted(DEFAULT_DELETED);
        return institution;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institution createUpdatedEntity(EntityManager em) {
        Institution institution = new Institution()
            .name(UPDATED_NAME)
            .tradeName(UPDATED_TRADE_NAME)
            .taxNumber(UPDATED_TAX_NUMBER)
            .url(UPDATED_URL)
            .deleted(UPDATED_DELETED);
        return institution;
    }

    @BeforeEach
    public void initTest() {
        institution = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstitution() throws Exception {
        int databaseSizeBeforeCreate = institutionRepository.findAll().size();
        // Create the Institution
        restInstitutionMockMvc.perform(post("/api/institutions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(institution)))
            .andExpect(status().isCreated());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeCreate + 1);
        Institution testInstitution = institutionList.get(institutionList.size() - 1);
        assertThat(testInstitution.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstitution.getTradeName()).isEqualTo(DEFAULT_TRADE_NAME);
        assertThat(testInstitution.getTaxNumber()).isEqualTo(DEFAULT_TAX_NUMBER);
        assertThat(testInstitution.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testInstitution.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createInstitutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = institutionRepository.findAll().size();

        // Create the Institution with an existing ID
        institution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstitutionMockMvc.perform(post("/api/institutions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(institution)))
            .andExpect(status().isBadRequest());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstitutions() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        // Get all the institutionList
        restInstitutionMockMvc.perform(get("/api/institutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institution.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].tradeName").value(hasItem(DEFAULT_TRADE_NAME)))
            .andExpect(jsonPath("$.[*].taxNumber").value(hasItem(DEFAULT_TAX_NUMBER)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInstitution() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        // Get the institution
        restInstitutionMockMvc.perform(get("/api/institutions/{id}", institution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(institution.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.tradeName").value(DEFAULT_TRADE_NAME))
            .andExpect(jsonPath("$.taxNumber").value(DEFAULT_TAX_NUMBER))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingInstitution() throws Exception {
        // Get the institution
        restInstitutionMockMvc.perform(get("/api/institutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitution() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();

        // Update the institution
        Institution updatedInstitution = institutionRepository.findById(institution.getId()).get();
        // Disconnect from session so that the updates on updatedInstitution are not directly saved in db
        em.detach(updatedInstitution);
        updatedInstitution
            .name(UPDATED_NAME)
            .tradeName(UPDATED_TRADE_NAME)
            .taxNumber(UPDATED_TAX_NUMBER)
            .url(UPDATED_URL)
            .deleted(UPDATED_DELETED);

        restInstitutionMockMvc.perform(put("/api/institutions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstitution)))
            .andExpect(status().isOk());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
        Institution testInstitution = institutionList.get(institutionList.size() - 1);
        assertThat(testInstitution.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstitution.getTradeName()).isEqualTo(UPDATED_TRADE_NAME);
        assertThat(testInstitution.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
        assertThat(testInstitution.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testInstitution.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingInstitution() throws Exception {
        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstitutionMockMvc.perform(put("/api/institutions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(institution)))
            .andExpect(status().isBadRequest());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstitution() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        int databaseSizeBeforeDelete = institutionRepository.findAll().size();

        // Delete the institution
        restInstitutionMockMvc.perform(delete("/api/institutions/{id}", institution.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
