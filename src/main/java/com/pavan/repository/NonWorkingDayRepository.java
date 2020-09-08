package com.pavan.repository;

import com.pavan.domain.NonWorkingDay;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NonWorkingDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NonWorkingDayRepository extends JpaRepository<NonWorkingDay, Long> {
}
