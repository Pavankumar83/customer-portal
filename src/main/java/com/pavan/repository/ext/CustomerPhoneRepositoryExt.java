package com.pavan.repository.ext;

import com.pavan.domain.CustomerPhone;
import com.pavan.repository.CustomerEmailRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerPhoneRepositoryExt extends CustomerEmailRepository {

    @Query("select cp from CustomerPhone cp where cp.customer.id=:id")
    List<CustomerPhone> findPhoneById(@Param("id") Long id);
}
