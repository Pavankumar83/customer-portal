package com.pavan.dto;

import com.pavan.domain.enumeration.TransactionMode;
import com.pavan.domain.enumeration.TransactionType;

import java.time.Instant;

public class AvailableTransactionDTO {
    private String transactionId;
    private TransactionType transactionType;
    private TransactionMode transactionMode;
    private Double transAmount;
    private Instant dateOfTransaction;

    public AvailableTransactionDTO() {
    }

    public AvailableTransactionDTO(String transactionId, TransactionType transactionType, TransactionMode transactionMode, Double transAmount, Instant dateOfTransaction) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionMode = transactionMode;
        this.transAmount = transAmount;
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionMode getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(TransactionMode transactionMode) {
        this.transactionMode = transactionMode;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public Instant getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Instant dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }
}
