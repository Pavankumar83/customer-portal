package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.LegalEntity;
import com.pavan.repository.LegalEntityRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LegalEntityResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LegalEntityResourceIT {

    private static final String DEFAULT_COMMERCIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMERCIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_BUSINESS_CLOSED = false;
    private static final Boolean UPDATED_BUSINESS_CLOSED = true;

    private static final String DEFAULT_BUSINESS_AREA = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_AREA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private LegalEntityRepository legalEntityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLegalEntityMockMvc;

    private LegalEntity legalEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LegalEntity createEntity(EntityManager em) {
        LegalEntity legalEntity = new LegalEntity()
            .commercialName(DEFAULT_COMMERCIAL_NAME)
            .taxNumber(DEFAULT_TAX_NUMBER)
            .title(DEFAULT_TITLE)
            .dateOfStart(DEFAULT_DATE_OF_START)
            .businessClosed(DEFAULT_BUSINESS_CLOSED)
            .businessArea(DEFAULT_BUSINESS_AREA)
            .deleted(DEFAULT_DELETED);
        return legalEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LegalEntity createUpdatedEntity(EntityManager em) {
        LegalEntity legalEntity = new LegalEntity()
            .commercialName(UPDATED_COMMERCIAL_NAME)
            .taxNumber(UPDATED_TAX_NUMBER)
            .title(UPDATED_TITLE)
            .dateOfStart(UPDATED_DATE_OF_START)
            .businessClosed(UPDATED_BUSINESS_CLOSED)
            .businessArea(UPDATED_BUSINESS_AREA)
            .deleted(UPDATED_DELETED);
        return legalEntity;
    }

    @BeforeEach
    public void initTest() {
        legalEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createLegalEntity() throws Exception {
        int databaseSizeBeforeCreate = legalEntityRepository.findAll().size();
        // Create the LegalEntity
        restLegalEntityMockMvc.perform(post("/api/legal-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(legalEntity)))
            .andExpect(status().isCreated());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeCreate + 1);
        LegalEntity testLegalEntity = legalEntityList.get(legalEntityList.size() - 1);
        assertThat(testLegalEntity.getCommercialName()).isEqualTo(DEFAULT_COMMERCIAL_NAME);
        assertThat(testLegalEntity.getTaxNumber()).isEqualTo(DEFAULT_TAX_NUMBER);
        assertThat(testLegalEntity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLegalEntity.getDateOfStart()).isEqualTo(DEFAULT_DATE_OF_START);
        assertThat(testLegalEntity.isBusinessClosed()).isEqualTo(DEFAULT_BUSINESS_CLOSED);
        assertThat(testLegalEntity.getBusinessArea()).isEqualTo(DEFAULT_BUSINESS_AREA);
        assertThat(testLegalEntity.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createLegalEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = legalEntityRepository.findAll().size();

        // Create the LegalEntity with an existing ID
        legalEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegalEntityMockMvc.perform(post("/api/legal-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(legalEntity)))
            .andExpect(status().isBadRequest());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLegalEntities() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        // Get all the legalEntityList
        restLegalEntityMockMvc.perform(get("/api/legal-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legalEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].commercialName").value(hasItem(DEFAULT_COMMERCIAL_NAME)))
            .andExpect(jsonPath("$.[*].taxNumber").value(hasItem(DEFAULT_TAX_NUMBER)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].dateOfStart").value(hasItem(DEFAULT_DATE_OF_START.toString())))
            .andExpect(jsonPath("$.[*].businessClosed").value(hasItem(DEFAULT_BUSINESS_CLOSED.booleanValue())))
            .andExpect(jsonPath("$.[*].businessArea").value(hasItem(DEFAULT_BUSINESS_AREA)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLegalEntity() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        // Get the legalEntity
        restLegalEntityMockMvc.perform(get("/api/legal-entities/{id}", legalEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(legalEntity.getId().intValue()))
            .andExpect(jsonPath("$.commercialName").value(DEFAULT_COMMERCIAL_NAME))
            .andExpect(jsonPath("$.taxNumber").value(DEFAULT_TAX_NUMBER))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.dateOfStart").value(DEFAULT_DATE_OF_START.toString()))
            .andExpect(jsonPath("$.businessClosed").value(DEFAULT_BUSINESS_CLOSED.booleanValue()))
            .andExpect(jsonPath("$.businessArea").value(DEFAULT_BUSINESS_AREA))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingLegalEntity() throws Exception {
        // Get the legalEntity
        restLegalEntityMockMvc.perform(get("/api/legal-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLegalEntity() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();

        // Update the legalEntity
        LegalEntity updatedLegalEntity = legalEntityRepository.findById(legalEntity.getId()).get();
        // Disconnect from session so that the updates on updatedLegalEntity are not directly saved in db
        em.detach(updatedLegalEntity);
        updatedLegalEntity
            .commercialName(UPDATED_COMMERCIAL_NAME)
            .taxNumber(UPDATED_TAX_NUMBER)
            .title(UPDATED_TITLE)
            .dateOfStart(UPDATED_DATE_OF_START)
            .businessClosed(UPDATED_BUSINESS_CLOSED)
            .businessArea(UPDATED_BUSINESS_AREA)
            .deleted(UPDATED_DELETED);

        restLegalEntityMockMvc.perform(put("/api/legal-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLegalEntity)))
            .andExpect(status().isOk());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
        LegalEntity testLegalEntity = legalEntityList.get(legalEntityList.size() - 1);
        assertThat(testLegalEntity.getCommercialName()).isEqualTo(UPDATED_COMMERCIAL_NAME);
        assertThat(testLegalEntity.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
        assertThat(testLegalEntity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLegalEntity.getDateOfStart()).isEqualTo(UPDATED_DATE_OF_START);
        assertThat(testLegalEntity.isBusinessClosed()).isEqualTo(UPDATED_BUSINESS_CLOSED);
        assertThat(testLegalEntity.getBusinessArea()).isEqualTo(UPDATED_BUSINESS_AREA);
        assertThat(testLegalEntity.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingLegalEntity() throws Exception {
        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegalEntityMockMvc.perform(put("/api/legal-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(legalEntity)))
            .andExpect(status().isBadRequest());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLegalEntity() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        int databaseSizeBeforeDelete = legalEntityRepository.findAll().size();

        // Delete the legalEntity
        restLegalEntityMockMvc.perform(delete("/api/legal-entities/{id}", legalEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
