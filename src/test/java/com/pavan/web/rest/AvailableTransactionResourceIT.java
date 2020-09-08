package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.AvailableTransaction;
import com.pavan.repository.AvailableTransactionRepository;

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

import com.pavan.domain.enumeration.TransactionType;
import com.pavan.domain.enumeration.TransactionMode;
/**
 * Integration tests for the {@link AvailableTransactionResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvailableTransactionResourceIT {

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final TransactionType DEFAULT_TRANSACTION_TYPE = TransactionType.INTEREST;
    private static final TransactionType UPDATED_TRANSACTION_TYPE = TransactionType.DIVIDENDS;

    private static final TransactionMode DEFAULT_TRANSACTION_MODE = TransactionMode.DEBIT;
    private static final TransactionMode UPDATED_TRANSACTION_MODE = TransactionMode.CREDIT;

    private static final Double DEFAULT_TRANS_AMOUNT = 1D;
    private static final Double UPDATED_TRANS_AMOUNT = 2D;

    private static final Instant DEFAULT_DATE_OF_TRANSACTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_TRANSACTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AvailableTransactionRepository availableTransactionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvailableTransactionMockMvc;

    private AvailableTransaction availableTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvailableTransaction createEntity(EntityManager em) {
        AvailableTransaction availableTransaction = new AvailableTransaction()
            .transactionId(DEFAULT_TRANSACTION_ID)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .transactionMode(DEFAULT_TRANSACTION_MODE)
            .transAmount(DEFAULT_TRANS_AMOUNT)
            .dateOfTransaction(DEFAULT_DATE_OF_TRANSACTION);
        return availableTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvailableTransaction createUpdatedEntity(EntityManager em) {
        AvailableTransaction availableTransaction = new AvailableTransaction()
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionMode(UPDATED_TRANSACTION_MODE)
            .transAmount(UPDATED_TRANS_AMOUNT)
            .dateOfTransaction(UPDATED_DATE_OF_TRANSACTION);
        return availableTransaction;
    }

    @BeforeEach
    public void initTest() {
        availableTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvailableTransaction() throws Exception {
        int databaseSizeBeforeCreate = availableTransactionRepository.findAll().size();
        // Create the AvailableTransaction
        restAvailableTransactionMockMvc.perform(post("/api/available-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(availableTransaction)))
            .andExpect(status().isCreated());

        // Validate the AvailableTransaction in the database
        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        AvailableTransaction testAvailableTransaction = availableTransactionList.get(availableTransactionList.size() - 1);
        assertThat(testAvailableTransaction.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testAvailableTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testAvailableTransaction.getTransactionMode()).isEqualTo(DEFAULT_TRANSACTION_MODE);
        assertThat(testAvailableTransaction.getTransAmount()).isEqualTo(DEFAULT_TRANS_AMOUNT);
        assertThat(testAvailableTransaction.getDateOfTransaction()).isEqualTo(DEFAULT_DATE_OF_TRANSACTION);
    }

    @Test
    @Transactional
    public void createAvailableTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = availableTransactionRepository.findAll().size();

        // Create the AvailableTransaction with an existing ID
        availableTransaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvailableTransactionMockMvc.perform(post("/api/available-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(availableTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the AvailableTransaction in the database
        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = availableTransactionRepository.findAll().size();
        // set the field null
        availableTransaction.setTransactionType(null);

        // Create the AvailableTransaction, which fails.


        restAvailableTransactionMockMvc.perform(post("/api/available-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(availableTransaction)))
            .andExpect(status().isBadRequest());

        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = availableTransactionRepository.findAll().size();
        // set the field null
        availableTransaction.setTransactionMode(null);

        // Create the AvailableTransaction, which fails.


        restAvailableTransactionMockMvc.perform(post("/api/available-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(availableTransaction)))
            .andExpect(status().isBadRequest());

        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = availableTransactionRepository.findAll().size();
        // set the field null
        availableTransaction.setTransAmount(null);

        // Create the AvailableTransaction, which fails.


        restAvailableTransactionMockMvc.perform(post("/api/available-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(availableTransaction)))
            .andExpect(status().isBadRequest());

        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfTransactionIsRequired() throws Exception {
        int databaseSizeBeforeTest = availableTransactionRepository.findAll().size();
        // set the field null
        availableTransaction.setDateOfTransaction(null);

        // Create the AvailableTransaction, which fails.


        restAvailableTransactionMockMvc.perform(post("/api/available-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(availableTransaction)))
            .andExpect(status().isBadRequest());

        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvailableTransactions() throws Exception {
        // Initialize the database
        availableTransactionRepository.saveAndFlush(availableTransaction);

        // Get all the availableTransactionList
        restAvailableTransactionMockMvc.perform(get("/api/available-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(availableTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transactionMode").value(hasItem(DEFAULT_TRANSACTION_MODE.toString())))
            .andExpect(jsonPath("$.[*].transAmount").value(hasItem(DEFAULT_TRANS_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateOfTransaction").value(hasItem(DEFAULT_DATE_OF_TRANSACTION.toString())));
    }
    
    @Test
    @Transactional
    public void getAvailableTransaction() throws Exception {
        // Initialize the database
        availableTransactionRepository.saveAndFlush(availableTransaction);

        // Get the availableTransaction
        restAvailableTransactionMockMvc.perform(get("/api/available-transactions/{id}", availableTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(availableTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.transactionMode").value(DEFAULT_TRANSACTION_MODE.toString()))
            .andExpect(jsonPath("$.transAmount").value(DEFAULT_TRANS_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dateOfTransaction").value(DEFAULT_DATE_OF_TRANSACTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAvailableTransaction() throws Exception {
        // Get the availableTransaction
        restAvailableTransactionMockMvc.perform(get("/api/available-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvailableTransaction() throws Exception {
        // Initialize the database
        availableTransactionRepository.saveAndFlush(availableTransaction);

        int databaseSizeBeforeUpdate = availableTransactionRepository.findAll().size();

        // Update the availableTransaction
        AvailableTransaction updatedAvailableTransaction = availableTransactionRepository.findById(availableTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedAvailableTransaction are not directly saved in db
        em.detach(updatedAvailableTransaction);
        updatedAvailableTransaction
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionMode(UPDATED_TRANSACTION_MODE)
            .transAmount(UPDATED_TRANS_AMOUNT)
            .dateOfTransaction(UPDATED_DATE_OF_TRANSACTION);

        restAvailableTransactionMockMvc.perform(put("/api/available-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvailableTransaction)))
            .andExpect(status().isOk());

        // Validate the AvailableTransaction in the database
        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeUpdate);
        AvailableTransaction testAvailableTransaction = availableTransactionList.get(availableTransactionList.size() - 1);
        assertThat(testAvailableTransaction.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testAvailableTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testAvailableTransaction.getTransactionMode()).isEqualTo(UPDATED_TRANSACTION_MODE);
        assertThat(testAvailableTransaction.getTransAmount()).isEqualTo(UPDATED_TRANS_AMOUNT);
        assertThat(testAvailableTransaction.getDateOfTransaction()).isEqualTo(UPDATED_DATE_OF_TRANSACTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAvailableTransaction() throws Exception {
        int databaseSizeBeforeUpdate = availableTransactionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvailableTransactionMockMvc.perform(put("/api/available-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(availableTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the AvailableTransaction in the database
        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvailableTransaction() throws Exception {
        // Initialize the database
        availableTransactionRepository.saveAndFlush(availableTransaction);

        int databaseSizeBeforeDelete = availableTransactionRepository.findAll().size();

        // Delete the availableTransaction
        restAvailableTransactionMockMvc.perform(delete("/api/available-transactions/{id}", availableTransaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvailableTransaction> availableTransactionList = availableTransactionRepository.findAll();
        assertThat(availableTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
