package com.pavan.repository.ext;

import com.pavan.domain.CustomerEmail;
import com.pavan.repository.CustomerEmailRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerEmailRepositoryExt  extends CustomerEmailRepository {

    @Query("select ce from CustomerEmail ce where ce.customer.id=:id")
    public List<CustomerEmail> findCustomerEmailsById(@Param("id") Long id);
}
