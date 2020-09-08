package com.pavan.dto;

import com.pavan.domain.enumeration.CustomerType;
import com.pavan.domain.enumeration.IdentificationType;

public class CustomerDTO {
    private String name;
    private Long id;
    private CustomerType customerType;
    private IdentificationType identificationType;

    public CustomerDTO(String name, Long id, CustomerType customerType, IdentificationType identificationType) {
        this.name = name;
        this.id = id;
        this.customerType = customerType;
        this.identificationType = identificationType;
    }

    public CustomerDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }
}
