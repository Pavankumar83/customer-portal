package com.pavan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavan.domain.AvailableTransaction;
import com.pavan.domain.Customer;
import com.pavan.domain.Report;
import com.pavan.domain.enumeration.ReportType;
import com.pavan.dto.AvailableTransactionDTO;
import com.pavan.dto.CustomerDTO;
import com.pavan.dto.GeneratedReportDTO;
import com.pavan.dto.ReportSummaryDTO;
import com.pavan.repository.CustomerRepository;
import com.pavan.repository.ext.ReportRepositoryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    public static String IT3_REPORT_FILE_PATH = "Z:\\pavan\\reportDataTest";
    @Autowired
    private ReportRepositoryExt reportRepositoryExt;

    @Autowired
    private CustomerRepository customerRepository;

    public GeneratedReportDTO generateReport(Long id, Instant startDate, Instant endDate, ReportType reportType) throws IOException {

        Optional<Customer> customer = customerRepository.findById(id);
        Instant now = Instant.now();
        Customer customer1 = customer.get();
        final String reportame = reportType + "_" + customer1.getId().toString() + "_" + customer1.getName() ;
        List<ReportSummaryDTO> reportSummaryData = reportRepositoryExt.getReportDataByCustomerIdAndDateOfTransaction(id, startDate, endDate);
        HashMap<String, Object> reportDataMap = new HashMap<>();
        reportDataMap.put("customer", mapCustomer(customer1));
        reportDataMap.put("dateOfGenerated", now);
        reportDataMap.put("reportingPeriod", startDate + " to  " + endDate);
        reportDataMap.put("reportSummaryData", reportSummaryData);
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(IT3_REPORT_FILE_PATH + "\\" + reportame + ".json");
        objectMapper.writeValue(file, reportDataMap);

        Report report = new Report();
        report.setCustomer(customer1);
        report.setStartPeriod(startDate);
        report.setEndPeriod(endDate);
        report.setGeneratedReportName(reportame + ".json");
        report.setGeneratedReportLocation(IT3_REPORT_FILE_PATH);
        report.setType(reportType);
        report.setName(reportame);

        List<AvailableTransaction> additionalReportData = reportRepositoryExt.getAdditionalReportData(id, startDate, endDate);
        List<AvailableTransactionDTO> availableTransactionDTOS = additionalReportData
            .parallelStream()
            .map(availableTransaction -> {
                AvailableTransactionDTO availableTransactionDTO = new AvailableTransactionDTO();
                availableTransactionDTO.setDateOfTransaction(availableTransaction.getDateOfTransaction());
                availableTransactionDTO.setTransactionId(availableTransaction.getTransactionId());
                availableTransactionDTO.setTransactionMode(availableTransaction.getTransactionMode());
                availableTransactionDTO.setTransactionType(availableTransaction.getTransactionType());
                availableTransactionDTO.setTransAmount(availableTransaction.getTransAmount());
                return availableTransactionDTO;
            }).collect(Collectors.toList());
        HashMap<String, Object> additinalDataMap = new HashMap<>();
        additinalDataMap.put("customer", mapCustomer(customer1));
        additinalDataMap.put("dateOfGenerated", now);
        additinalDataMap.put("reportingPeriod", startDate + " to  " + endDate);
        additinalDataMap.put("additionalReportData", availableTransactionDTOS);
        final String aifFileName = reportType + "_additinalInfo_" + customer1.getId().toString() + "_" + customer1.getName() + "_";
        File file2 = new File(IT3_REPORT_FILE_PATH + "\\" + aifFileName + ".json");
        objectMapper.writeValue(file2, additinalDataMap);

        report.setGeneratedAIFName(aifFileName);
        report.setGeneratedAIFLocation(IT3_REPORT_FILE_PATH);

        Report saved = reportRepositoryExt.save(report);

        GeneratedReportDTO generatedReportDTO = new GeneratedReportDTO();
        generatedReportDTO.setReport(saved);
        generatedReportDTO.setReportDataMap(reportDataMap);


        return generatedReportDTO;


    }

    private Report saveReport(Report report) {
        return this.reportRepositoryExt.save(report);
    }

    private CustomerDTO mapCustomer(Customer customer) {

        if (customer != null) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerType(customer.getCustomerType());
            customerDTO.setId(customer.getId());
            customerDTO.setIdentificationType(customer.getIdentificationType());
            customerDTO.setName(customer.getName());
            return customerDTO;
        }
        return null;
    }
}
