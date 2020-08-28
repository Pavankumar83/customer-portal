package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.BankInfo;
import com.pavan.repository.BankInfoRepository;

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
 * Integration tests for the {@link BankInfoResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BankInfoResourceExtIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_HOLDER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    @Autowired
    private BankInfoRepository bankInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankInfoMockMvc;

    private BankInfo bankInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankInfo createEntity(EntityManager em) {
        BankInfo bankInfo = new BankInfo()
            .name(DEFAULT_NAME)
            .accountHolder(DEFAULT_ACCOUNT_HOLDER)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .branchCode(DEFAULT_BRANCH_CODE)
            .branchAddress(DEFAULT_BRANCH_ADDRESS)
            .ifscCode(DEFAULT_IFSC_CODE);
        return bankInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankInfo createUpdatedEntity(EntityManager em) {
        BankInfo bankInfo = new BankInfo()
            .name(UPDATED_NAME)
            .accountHolder(UPDATED_ACCOUNT_HOLDER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .branchCode(UPDATED_BRANCH_CODE)
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .ifscCode(UPDATED_IFSC_CODE);
        return bankInfo;
    }

    @BeforeEach
    public void initTest() {
        bankInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankInfo() throws Exception {
        int databaseSizeBeforeCreate = bankInfoRepository.findAll().size();
        // Create the BankInfo
        restBankInfoMockMvc.perform(post("/api/bank-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankInfo)))
            .andExpect(status().isCreated());

        // Validate the BankInfo in the database
        List<BankInfo> bankInfoList = bankInfoRepository.findAll();
        assertThat(bankInfoList).hasSize(databaseSizeBeforeCreate + 1);
        BankInfo testBankInfo = bankInfoList.get(bankInfoList.size() - 1);
        assertThat(testBankInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankInfo.getAccountHolder()).isEqualTo(DEFAULT_ACCOUNT_HOLDER);
        assertThat(testBankInfo.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankInfo.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testBankInfo.getBranchAddress()).isEqualTo(DEFAULT_BRANCH_ADDRESS);
        assertThat(testBankInfo.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
    }

    @Test
    @Transactional
    public void createBankInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankInfoRepository.findAll().size();

        // Create the BankInfo with an existing ID
        bankInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankInfoMockMvc.perform(post("/api/bank-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankInfo)))
            .andExpect(status().isBadRequest());

        // Validate the BankInfo in the database
        List<BankInfo> bankInfoList = bankInfoRepository.findAll();
        assertThat(bankInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBankInfos() throws Exception {
        // Initialize the database
        bankInfoRepository.saveAndFlush(bankInfo);

        // Get all the bankInfoList
        restBankInfoMockMvc.perform(get("/api/bank-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].accountHolder").value(hasItem(DEFAULT_ACCOUNT_HOLDER)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].branchAddress").value(hasItem(DEFAULT_BRANCH_ADDRESS)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)));
    }

    @Test
    @Transactional
    public void getBankInfo() throws Exception {
        // Initialize the database
        bankInfoRepository.saveAndFlush(bankInfo);

        // Get the bankInfo
        restBankInfoMockMvc.perform(get("/api/bank-infos/{id}", bankInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.accountHolder").value(DEFAULT_ACCOUNT_HOLDER))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE))
            .andExpect(jsonPath("$.branchAddress").value(DEFAULT_BRANCH_ADDRESS))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingBankInfo() throws Exception {
        // Get the bankInfo
        restBankInfoMockMvc.perform(get("/api/bank-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankInfo() throws Exception {
        // Initialize the database
        bankInfoRepository.saveAndFlush(bankInfo);

        int databaseSizeBeforeUpdate = bankInfoRepository.findAll().size();

        // Update the bankInfo
        BankInfo updatedBankInfo = bankInfoRepository.findById(bankInfo.getId()).get();
        // Disconnect from session so that the updates on updatedBankInfo are not directly saved in db
        em.detach(updatedBankInfo);
        updatedBankInfo
            .name(UPDATED_NAME)
            .accountHolder(UPDATED_ACCOUNT_HOLDER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .branchCode(UPDATED_BRANCH_CODE)
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .ifscCode(UPDATED_IFSC_CODE);

        restBankInfoMockMvc.perform(put("/api/bank-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankInfo)))
            .andExpect(status().isOk());

        // Validate the BankInfo in the database
        List<BankInfo> bankInfoList = bankInfoRepository.findAll();
        assertThat(bankInfoList).hasSize(databaseSizeBeforeUpdate);
        BankInfo testBankInfo = bankInfoList.get(bankInfoList.size() - 1);
        assertThat(testBankInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankInfo.getAccountHolder()).isEqualTo(UPDATED_ACCOUNT_HOLDER);
        assertThat(testBankInfo.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankInfo.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testBankInfo.getBranchAddress()).isEqualTo(UPDATED_BRANCH_ADDRESS);
        assertThat(testBankInfo.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBankInfo() throws Exception {
        int databaseSizeBeforeUpdate = bankInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankInfoMockMvc.perform(put("/api/bank-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankInfo)))
            .andExpect(status().isBadRequest());

        // Validate the BankInfo in the database
        List<BankInfo> bankInfoList = bankInfoRepository.findAll();
        assertThat(bankInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankInfo() throws Exception {
        // Initialize the database
        bankInfoRepository.saveAndFlush(bankInfo);

        int databaseSizeBeforeDelete = bankInfoRepository.findAll().size();

        // Delete the bankInfo
        restBankInfoMockMvc.perform(delete("/api/bank-infos/{id}", bankInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankInfo> bankInfoList = bankInfoRepository.findAll();
        assertThat(bankInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
