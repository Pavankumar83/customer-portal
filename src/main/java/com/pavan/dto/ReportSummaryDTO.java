package com.pavan.dto;


import com.pavan.domain.enumeration.TransactionMode;
import com.pavan.domain.enumeration.TransactionType;

public class ReportSummaryDTO {
    private TransactionType transactionType;
    private TransactionMode transactionMode;
    private Double summary;

    public ReportSummaryDTO() {
    }

    public ReportSummaryDTO(TransactionType transactionType, TransactionMode transactionMode, Double summary) {
        this.transactionType = transactionType;
        this.transactionMode = transactionMode;
        this.summary = summary;
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

    public Double getSummary() {
        return summary;
    }

    public void setSummary(Double summary) {
        this.summary = summary;
    }
}
