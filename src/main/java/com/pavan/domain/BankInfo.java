package com.pavan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BankInfo.
 */
@Entity
@Table(name = "bank_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_holder")
    private String accountHolder;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "branch_address")
    private String branchAddress;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @OneToMany(mappedBy = "account")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AvailableTransaction> availableTransactions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "bankInfos", allowSetters = true)
    private Institution institution;

    @ManyToOne
    @JsonIgnoreProperties(value = "bankInfos", allowSetters = true)
    private Customer customer;

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

    public BankInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankInfo accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BankInfo accountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
        return this;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public BankInfo branchCode(String branchCode) {
        this.branchCode = branchCode;
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public BankInfo branchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
        return this;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public BankInfo ifscCode(String ifscCode) {
        this.ifscCode = ifscCode;
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Set<AvailableTransaction> getAvailableTransactions() {
        return availableTransactions;
    }

    public BankInfo availableTransactions(Set<AvailableTransaction> availableTransactions) {
        this.availableTransactions = availableTransactions;
        return this;
    }

    public BankInfo addAvailableTransaction(AvailableTransaction availableTransaction) {
        this.availableTransactions.add(availableTransaction);
        availableTransaction.setAccount(this);
        return this;
    }

    public BankInfo removeAvailableTransaction(AvailableTransaction availableTransaction) {
        this.availableTransactions.remove(availableTransaction);
        availableTransaction.setAccount(null);
        return this;
    }

    public void setAvailableTransactions(Set<AvailableTransaction> availableTransactions) {
        this.availableTransactions = availableTransactions;
    }

    public Institution getInstitution() {
        return institution;
    }

    public BankInfo institution(Institution institution) {
        this.institution = institution;
        return this;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BankInfo customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankInfo)) {
            return false;
        }
        return id != null && id.equals(((BankInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountHolder='" + getAccountHolder() + "'" +
            ", branchCode='" + getBranchCode() + "'" +
            ", branchAddress='" + getBranchAddress() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            "}";
    }
}
