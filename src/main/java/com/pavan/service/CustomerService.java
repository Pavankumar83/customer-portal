package com.pavan.service;

import com.pavan.domain.Customer;
import com.pavan.repository.ext.CustomerRepositoryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepositoryExt customerRepositoryExt;

    public Page<Customer> getCustomersPageById(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepositoryExt.findAll(pageRequest);
        return customerPage;
    }
    public Page<Customer> getCustomersPageByField(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name").descending());
      return customerRepositoryExt.findAll(pageRequest);

    }

    public Page<Customer> getCustomersByPageNameAndIdentificationType(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name").ascending().and(Sort.by("identificationType")));
        return customerRepositoryExt.findAll(pageRequest);
    }
}
