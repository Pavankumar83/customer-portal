package com.pavan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.pavan.domain.enumeration.TransactionType;

import com.pavan.domain.enumeration.TransactionMode;

/**
 * A AvailableTransaction.
 */
@Entity
@Table(name = "available_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvailableTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_mode", nullable = false)
    private TransactionMode transactionMode;

    @NotNull
    @Column(name = "trans_amount", nullable = false)
    private Double transAmount;

    @NotNull
    @Column(name = "date_of_transaction", nullable = false)
    private Instant dateOfTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "availableTransactions", allowSetters = true)
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "availableTransactions", allowSetters = true)
    private BankInfo account;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public AvailableTransaction transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public AvailableTransaction transactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionMode getTransactionMode() {
        return transactionMode;
    }

    public AvailableTransaction transactionMode(TransactionMode transactionMode) {
        this.transactionMode = transactionMode;
        return this;
    }

    public void setTransactionMode(TransactionMode transactionMode) {
        this.transactionMode = transactionMode;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public AvailableTransaction transAmount(Double transAmount) {
        this.transAmount = transAmount;
        return this;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public Instant getDateOfTransaction() {
        return dateOfTransaction;
    }

    public AvailableTransaction dateOfTransaction(Instant dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
        return this;
    }

    public void setDateOfTransaction(Instant dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public Customer getCustomer() {
        return customer;
    }

    public AvailableTransaction customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BankInfo getAccount() {
        return account;
    }

    public AvailableTransaction account(BankInfo bankInfo) {
        this.account = bankInfo;
        return this;
    }

    public void setAccount(BankInfo bankInfo) {
        this.account = bankInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvailableTransaction)) {
            return false;
        }
        return id != null && id.equals(((AvailableTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvailableTransaction{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", transactionMode='" + getTransactionMode() + "'" +
            ", transAmount=" + getTransAmount() +
            ", dateOfTransaction='" + getDateOfTransaction() + "'" +
            "}";
    }
}
