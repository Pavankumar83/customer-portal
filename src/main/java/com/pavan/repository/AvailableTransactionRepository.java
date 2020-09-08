package com.pavan.repository;

import com.pavan.domain.AvailableTransaction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvailableTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvailableTransactionRepository extends JpaRepository<AvailableTransaction, Long> {
}
