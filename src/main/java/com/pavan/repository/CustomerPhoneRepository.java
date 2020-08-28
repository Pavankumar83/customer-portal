package com.pavan.repository;

import com.pavan.domain.CustomerPhone;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPhoneRepository extends JpaRepository<CustomerPhone, Long> {
}
