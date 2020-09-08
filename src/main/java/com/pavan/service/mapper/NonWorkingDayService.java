package com.pavan.service.mapper;

import com.pavan.domain.NonWorkingDay;
import com.pavan.repository.ext.NonWorkingDayRepositoryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NonWorkingDayService {

    @Autowired
    NonWorkingDayRepositoryExt nonWorkingDayRepositoryExt;


    public LocalDate calculateFutureWorkingDay(Instant startDate, Integer noOfdays) {
        List<Instant> nonWorkingDaysList =
            nonWorkingDayRepositoryExt.findAll().stream().map(NonWorkingDay::getNonWorkingDay).collect(Collectors.toList());
        List<LocalDate> collectNonWorkingDays = nonWorkingDaysList
            .parallelStream()
            .map(instant -> LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate())
            .collect(Collectors.toList());
        LocalDate futureDate =LocalDateTime.ofInstant(startDate, ZoneOffset.UTC).toLocalDate();
        //int futureDate=0;
        int businessDays = 0;
        while (true) {
            System.out.println("futureDate = " + futureDate);
            boolean isWorkingDay = true;
            for (LocalDate day : collectNonWorkingDays) {
                if (day.equals(futureDate)) {
                    isWorkingDay = false;
                    System.out.println("holiday = " + day);
                    break;
                }
            }
            if (isWorkingDay) {
                businessDays++;
                if (businessDays == noOfdays) break;
            }
            futureDate = futureDate.plus(1, ChronoUnit.DAYS);

        }

        return futureDate;
    }

    public int calculateNumberOfWorkingDays(Instant startDate, Instant endDate) {
        List<Instant> nonWorkingDaysList =
            nonWorkingDayRepositoryExt.findAll().stream().map(NonWorkingDay::getNonWorkingDay).collect(Collectors.toList());
        List<LocalDate> collectNonWorkingDays = nonWorkingDaysList
            .parallelStream()
            .map(instant ->LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate())
            .collect(Collectors.toList());

        LocalDate startDateLocal = LocalDateTime.ofInstant(startDate, ZoneOffset.UTC).toLocalDate();
        LocalDate endDateLocal = LocalDateTime.ofInstant(endDate, ZoneOffset.UTC).toLocalDate();

        int businessDays = 0;
        while (true) {
            //System.out.println("futureDate = " + futureDate);
            boolean isWorkingDay = true;
            for (LocalDate day : collectNonWorkingDays) {
                if (day.equals(startDateLocal)) {
                    isWorkingDay = false;
                    System.out.println("holiday = " + day);
                    break;
                }
            }
            if (isWorkingDay) {
                businessDays++;
            }
            if(startDateLocal.equals(endDateLocal)) break;
            startDateLocal = startDateLocal.plus(1, ChronoUnit.DAYS);
        }

        return businessDays;
    }
}

