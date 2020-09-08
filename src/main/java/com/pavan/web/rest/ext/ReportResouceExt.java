package com.pavan.web.rest.ext;

import com.pavan.domain.enumeration.ReportType;
import com.pavan.dto.GeneratedReportDTO;
import com.pavan.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;

@RestController
@RequestMapping("/api")
public class ReportResouceExt {


    @Autowired
    public ReportService reportService;

    @GetMapping(path = "/report/{id}/{reportType}")
    public ResponseEntity<GeneratedReportDTO> generateReportByCustomerIdAndReportType(@PathVariable("id") Long id,
                                                                                     @RequestParam("startDate") Instant startDate,
                                                                                     @RequestParam("endDate") Instant endDate,
                                                                                     @PathVariable("reportType") ReportType reportType) throws IOException {
        GeneratedReportDTO generatedReportDTO = reportService.generateReport(id, startDate, endDate, reportType);
        return new ResponseEntity<>(generatedReportDTO, HttpStatus.OK);
    }
}
