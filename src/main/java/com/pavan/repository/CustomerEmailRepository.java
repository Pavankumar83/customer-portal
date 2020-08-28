package com.pavan.repository;

import com.pavan.domain.CustomerEmail;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerEmailRepository extends JpaRepository<CustomerEmail, Long> {
}
