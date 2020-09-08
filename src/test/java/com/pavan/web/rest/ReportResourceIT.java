package com.pavan.web.rest;

import com.pavan.CustomerPortalApp;
import com.pavan.domain.Report;
import com.pavan.repository.ReportRepository;

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

import com.pavan.domain.enumeration.ReportType;
/**
 * Integration tests for the {@link ReportResource} REST controller.
 */
@SpringBootTest(classes = CustomerPortalApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportResourceIT {

    private static final ReportType DEFAULT_TYPE = ReportType.IT3B;
    private static final ReportType UPDATED_TYPE = ReportType.IT3C;

    private static final Instant DEFAULT_START_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENERATED_REPORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_REPORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENERATED_AIF_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_AIF_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENERATED_REPORT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_REPORT_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_GENERATED_AIF_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_AIF_LOCATION = "BBBBBBBBBB";

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportMockMvc;

    private Report report;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Report createEntity(EntityManager em) {
        Report report = new Report()
            .type(DEFAULT_TYPE)
            .startPeriod(DEFAULT_START_PERIOD)
            .endPeriod(DEFAULT_END_PERIOD)
            .name(DEFAULT_NAME)
            .generatedReportName(DEFAULT_GENERATED_REPORT_NAME)
            .generatedAIFName(DEFAULT_GENERATED_AIF_NAME)
            .generatedReportLocation(DEFAULT_GENERATED_REPORT_LOCATION)
            .generatedAIFLocation(DEFAULT_GENERATED_AIF_LOCATION);
        return report;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Report createUpdatedEntity(EntityManager em) {
        Report report = new Report()
            .type(UPDATED_TYPE)
            .startPeriod(UPDATED_START_PERIOD)
            .endPeriod(UPDATED_END_PERIOD)
            .name(UPDATED_NAME)
            .generatedReportName(UPDATED_GENERATED_REPORT_NAME)
            .generatedAIFName(UPDATED_GENERATED_AIF_NAME)
            .generatedReportLocation(UPDATED_GENERATED_REPORT_LOCATION)
            .generatedAIFLocation(UPDATED_GENERATED_AIF_LOCATION);
        return report;
    }

    @BeforeEach
    public void initTest() {
        report = createEntity(em);
    }

    @Test
    @Transactional
    public void createReport() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();
        // Create the Report
        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isCreated());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate + 1);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReport.getStartPeriod()).isEqualTo(DEFAULT_START_PERIOD);
        assertThat(testReport.getEndPeriod()).isEqualTo(DEFAULT_END_PERIOD);
        assertThat(testReport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReport.getGeneratedReportName()).isEqualTo(DEFAULT_GENERATED_REPORT_NAME);
        assertThat(testReport.getGeneratedAIFName()).isEqualTo(DEFAULT_GENERATED_AIF_NAME);
        assertThat(testReport.getGeneratedReportLocation()).isEqualTo(DEFAULT_GENERATED_REPORT_LOCATION);
        assertThat(testReport.getGeneratedAIFLocation()).isEqualTo(DEFAULT_GENERATED_AIF_LOCATION);
    }

    @Test
    @Transactional
    public void createReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report with an existing ID
        report.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setType(null);

        // Create the Report, which fails.


        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setStartPeriod(null);

        // Create the Report, which fails.


        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setEndPeriod(null);

        // Create the Report, which fails.


        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setName(null);

        // Create the Report, which fails.


        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneratedReportNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setGeneratedReportName(null);

        // Create the Report, which fails.


        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneratedAIFNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setGeneratedAIFName(null);

        // Create the Report, which fails.


        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneratedReportLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setGeneratedReportLocation(null);

        // Create the Report, which fails.


        restReportMockMvc.perform(post("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReports() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList
        restReportMockMvc.perform(get("/api/reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startPeriod").value(hasItem(DEFAULT_START_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].endPeriod").value(hasItem(DEFAULT_END_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].generatedReportName").value(hasItem(DEFAULT_GENERATED_REPORT_NAME)))
            .andExpect(jsonPath("$.[*].generatedAIFName").value(hasItem(DEFAULT_GENERATED_AIF_NAME)))
            .andExpect(jsonPath("$.[*].generatedReportLocation").value(hasItem(DEFAULT_GENERATED_REPORT_LOCATION)))
            .andExpect(jsonPath("$.[*].generatedAIFLocation").value(hasItem(DEFAULT_GENERATED_AIF_LOCATION)));
    }
    
    @Test
    @Transactional
    public void getReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(report.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.startPeriod").value(DEFAULT_START_PERIOD.toString()))
            .andExpect(jsonPath("$.endPeriod").value(DEFAULT_END_PERIOD.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.generatedReportName").value(DEFAULT_GENERATED_REPORT_NAME))
            .andExpect(jsonPath("$.generatedAIFName").value(DEFAULT_GENERATED_AIF_NAME))
            .andExpect(jsonPath("$.generatedReportLocation").value(DEFAULT_GENERATED_REPORT_LOCATION))
            .andExpect(jsonPath("$.generatedAIFLocation").value(DEFAULT_GENERATED_AIF_LOCATION));
    }
    @Test
    @Transactional
    public void getNonExistingReport() throws Exception {
        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Update the report
        Report updatedReport = reportRepository.findById(report.getId()).get();
        // Disconnect from session so that the updates on updatedReport are not directly saved in db
        em.detach(updatedReport);
        updatedReport
            .type(UPDATED_TYPE)
            .startPeriod(UPDATED_START_PERIOD)
            .endPeriod(UPDATED_END_PERIOD)
            .name(UPDATED_NAME)
            .generatedReportName(UPDATED_GENERATED_REPORT_NAME)
            .generatedAIFName(UPDATED_GENERATED_AIF_NAME)
            .generatedReportLocation(UPDATED_GENERATED_REPORT_LOCATION)
            .generatedAIFLocation(UPDATED_GENERATED_AIF_LOCATION);

        restReportMockMvc.perform(put("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReport)))
            .andExpect(status().isOk());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReport.getStartPeriod()).isEqualTo(UPDATED_START_PERIOD);
        assertThat(testReport.getEndPeriod()).isEqualTo(UPDATED_END_PERIOD);
        assertThat(testReport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReport.getGeneratedReportName()).isEqualTo(UPDATED_GENERATED_REPORT_NAME);
        assertThat(testReport.getGeneratedAIFName()).isEqualTo(UPDATED_GENERATED_AIF_NAME);
        assertThat(testReport.getGeneratedReportLocation()).isEqualTo(UPDATED_GENERATED_REPORT_LOCATION);
        assertThat(testReport.getGeneratedAIFLocation()).isEqualTo(UPDATED_GENERATED_AIF_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingReport() throws Exception {
        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportMockMvc.perform(put("/api/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(report)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        int databaseSizeBeforeDelete = reportRepository.findAll().size();

        // Delete the report
        restReportMockMvc.perform(delete("/api/reports/{id}", report.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
