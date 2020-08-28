package com.pavan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.pavan.domain.enumeration.IdentificationType;

import com.pavan.domain.enumeration.CustomerType;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "identification_type")
    private IdentificationType identificationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CustomerEmail> customerEmails = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CustomerPhone> customerPhones = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CustomerAddress> customerAddresses = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BankInfo> bankInfos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private Institution institution;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public Customer identificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
        return this;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public Customer customerType(CustomerType customerType) {
        this.customerType = customerType;
        return this;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Customer deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<CustomerEmail> getCustomerEmails() {
        return customerEmails;
    }

    public Customer customerEmails(Set<CustomerEmail> customerEmails) {
        this.customerEmails = customerEmails;
        return this;
    }

    public Customer addCustomerEmail(CustomerEmail customerEmail) {
        this.customerEmails.add(customerEmail);
        customerEmail.setCustomer(this);
        return this;
    }

    public Customer removeCustomerEmail(CustomerEmail customerEmail) {
        this.customerEmails.remove(customerEmail);
        customerEmail.setCustomer(null);
        return this;
    }

    public void setCustomerEmails(Set<CustomerEmail> customerEmails) {
        this.customerEmails = customerEmails;
    }

    public Set<CustomerPhone> getCustomerPhones() {
        return customerPhones;
    }

    public Customer customerPhones(Set<CustomerPhone> customerPhones) {
        this.customerPhones = customerPhones;
        return this;
    }

    public Customer addCustomerPhone(CustomerPhone customerPhone) {
        this.customerPhones.add(customerPhone);
        customerPhone.setCustomer(this);
        return this;
    }

    public Customer removeCustomerPhone(CustomerPhone customerPhone) {
        this.customerPhones.remove(customerPhone);
        customerPhone.setCustomer(null);
        return this;
    }

    public void setCustomerPhones(Set<CustomerPhone> customerPhones) {
        this.customerPhones = customerPhones;
    }

    public Set<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    public Customer customerAddresses(Set<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
        return this;
    }

    public Customer addCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddresses.add(customerAddress);
        customerAddress.setCustomer(this);
        return this;
    }

    public Customer removeCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddresses.remove(customerAddress);
        customerAddress.setCustomer(null);
        return this;
    }

    public void setCustomerAddresses(Set<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    public Set<BankInfo> getBankInfos() {
        return bankInfos;
    }

    public Customer bankInfos(Set<BankInfo> bankInfos) {
        this.bankInfos = bankInfos;
        return this;
    }

    public Customer addBankInfo(BankInfo bankInfo) {
        this.bankInfos.add(bankInfo);
        bankInfo.setCustomer(this);
        return this;
    }

    public Customer removeBankInfo(BankInfo bankInfo) {
        this.bankInfos.remove(bankInfo);
        bankInfo.setCustomer(null);
        return this;
    }

    public void setBankInfos(Set<BankInfo> bankInfos) {
        this.bankInfos = bankInfos;
    }

    public Institution getInstitution() {
        return institution;
    }

    public Customer institution(Institution institution) {
        this.institution = institution;
        return this;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", identificationType='" + getIdentificationType() + "'" +
            ", customerType='" + getCustomerType() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
