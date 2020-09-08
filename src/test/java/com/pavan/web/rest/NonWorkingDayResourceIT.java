package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.NonWorkingDay;
import com.pavan.repository.NonWorkingDayRepository;

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
 * Integration tests for the {@link NonWorkingDayResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NonWorkingDayResourceIT {

    private static final Instant DEFAULT_NON_WORKING_DAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NON_WORKING_DAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private NonWorkingDayRepository nonWorkingDayRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNonWorkingDayMockMvc;

    private NonWorkingDay nonWorkingDay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NonWorkingDay createEntity(EntityManager em) {
        NonWorkingDay nonWorkingDay = new NonWorkingDay()
            .nonWorkingDay(DEFAULT_NON_WORKING_DAY)
            .deleted(DEFAULT_DELETED);
        return nonWorkingDay;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NonWorkingDay createUpdatedEntity(EntityManager em) {
        NonWorkingDay nonWorkingDay = new NonWorkingDay()
            .nonWorkingDay(UPDATED_NON_WORKING_DAY)
            .deleted(UPDATED_DELETED);
        return nonWorkingDay;
    }

    @BeforeEach
    public void initTest() {
        nonWorkingDay = createEntity(em);
    }

    @Test
    @Transactional
    public void createNonWorkingDay() throws Exception {
        int databaseSizeBeforeCreate = nonWorkingDayRepository.findAll().size();
        // Create the NonWorkingDay
        restNonWorkingDayMockMvc.perform(post("/api/non-working-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nonWorkingDay)))
            .andExpect(status().isCreated());

        // Validate the NonWorkingDay in the database
        List<NonWorkingDay> nonWorkingDayList = nonWorkingDayRepository.findAll();
        assertThat(nonWorkingDayList).hasSize(databaseSizeBeforeCreate + 1);
        NonWorkingDay testNonWorkingDay = nonWorkingDayList.get(nonWorkingDayList.size() - 1);
        assertThat(testNonWorkingDay.getNonWorkingDay()).isEqualTo(DEFAULT_NON_WORKING_DAY);
        assertThat(testNonWorkingDay.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createNonWorkingDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nonWorkingDayRepository.findAll().size();

        // Create the NonWorkingDay with an existing ID
        nonWorkingDay.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNonWorkingDayMockMvc.perform(post("/api/non-working-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nonWorkingDay)))
            .andExpect(status().isBadRequest());

        // Validate the NonWorkingDay in the database
        List<NonWorkingDay> nonWorkingDayList = nonWorkingDayRepository.findAll();
        assertThat(nonWorkingDayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNonWorkingDays() throws Exception {
        // Initialize the database
        nonWorkingDayRepository.saveAndFlush(nonWorkingDay);

        // Get all the nonWorkingDayList
        restNonWorkingDayMockMvc.perform(get("/api/non-working-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nonWorkingDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].nonWorkingDay").value(hasItem(DEFAULT_NON_WORKING_DAY.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNonWorkingDay() throws Exception {
        // Initialize the database
        nonWorkingDayRepository.saveAndFlush(nonWorkingDay);

        // Get the nonWorkingDay
        restNonWorkingDayMockMvc.perform(get("/api/non-working-days/{id}", nonWorkingDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nonWorkingDay.getId().intValue()))
            .andExpect(jsonPath("$.nonWorkingDay").value(DEFAULT_NON_WORKING_DAY.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingNonWorkingDay() throws Exception {
        // Get the nonWorkingDay
        restNonWorkingDayMockMvc.perform(get("/api/non-working-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNonWorkingDay() throws Exception {
        // Initialize the database
        nonWorkingDayRepository.saveAndFlush(nonWorkingDay);

        int databaseSizeBeforeUpdate = nonWorkingDayRepository.findAll().size();

        // Update the nonWorkingDay
        NonWorkingDay updatedNonWorkingDay = nonWorkingDayRepository.findById(nonWorkingDay.getId()).get();
        // Disconnect from session so that the updates on updatedNonWorkingDay are not directly saved in db
        em.detach(updatedNonWorkingDay);
        updatedNonWorkingDay
            .nonWorkingDay(UPDATED_NON_WORKING_DAY)
            .deleted(UPDATED_DELETED);

        restNonWorkingDayMockMvc.perform(put("/api/non-working-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNonWorkingDay)))
            .andExpect(status().isOk());

        // Validate the NonWorkingDay in the database
        List<NonWorkingDay> nonWorkingDayList = nonWorkingDayRepository.findAll();
        assertThat(nonWorkingDayList).hasSize(databaseSizeBeforeUpdate);
        NonWorkingDay testNonWorkingDay = nonWorkingDayList.get(nonWorkingDayList.size() - 1);
        assertThat(testNonWorkingDay.getNonWorkingDay()).isEqualTo(UPDATED_NON_WORKING_DAY);
        assertThat(testNonWorkingDay.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingNonWorkingDay() throws Exception {
        int databaseSizeBeforeUpdate = nonWorkingDayRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNonWorkingDayMockMvc.perform(put("/api/non-working-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nonWorkingDay)))
            .andExpect(status().isBadRequest());

        // Validate the NonWorkingDay in the database
        List<NonWorkingDay> nonWorkingDayList = nonWorkingDayRepository.findAll();
        assertThat(nonWorkingDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNonWorkingDay() throws Exception {
        // Initialize the database
        nonWorkingDayRepository.saveAndFlush(nonWorkingDay);

        int databaseSizeBeforeDelete = nonWorkingDayRepository.findAll().size();

        // Delete the nonWorkingDay
        restNonWorkingDayMockMvc.perform(delete("/api/non-working-days/{id}", nonWorkingDay.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NonWorkingDay> nonWorkingDayList = nonWorkingDayRepository.findAll();
        assertThat(nonWorkingDayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
