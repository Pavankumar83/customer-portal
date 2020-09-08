package com.pavan.web.rest.ext;

import com.pavan.service.mapper.NonWorkingDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class NonWorkingDayResourceExt {
    @Autowired
    private NonWorkingDayService nonWorkingDayService;

    @GetMapping(path = "/nonWorkingDay/{noOfDays}")
    public LocalDate getFutureBusinessDay(@RequestParam("startDate") Instant startDate, @PathVariable("noOfDays") Integer noOfdays) {

       return nonWorkingDayService.calculateFutureWorkingDay(startDate,noOfdays);
    }
    @GetMapping(path = "/dateDiff")
    public int getDateDifference(@RequestParam("startDate") Instant startDate, @RequestParam("endDate") Instant endDate ) {

        return nonWorkingDayService.calculateNumberOfWorkingDays(startDate, endDate);
    }
}
