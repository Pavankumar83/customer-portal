package com.pavan.repository;

import com.pavan.domain.LegalEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LegalEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long> {
}
