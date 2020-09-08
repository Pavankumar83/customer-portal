package com.pavan.dto;

import com.pavan.domain.Customer;
import com.pavan.domain.Report;

import java.util.HashMap;
import java.util.Map;

public class GeneratedReportDTO {
    private Map<String, Object> reportDataMap = new HashMap<>();
    private Report report;

    public GeneratedReportDTO() {
    }

    public GeneratedReportDTO(Map<String, Object> reportDataMap, Report report) {
        this.reportDataMap = reportDataMap;
        this.report = report;
    }

    public Map<String, Object> getReportDataMap() {
        return reportDataMap;
    }

    public void setReportDataMap(Map<String, Object> reportDataMap) {
        this.reportDataMap = reportDataMap;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
