package com.pavan.repository;

import com.pavan.domain.CustomerAddress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
}
