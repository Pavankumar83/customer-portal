package com.pavan.repository.ext;

import com.pavan.domain.Customer;
import com.pavan.repository.CustomerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositoryExt extends CustomerRepository, PagingAndSortingRepository<Customer, Long> {


}
