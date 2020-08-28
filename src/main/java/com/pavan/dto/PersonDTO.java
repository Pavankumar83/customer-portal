package com.pavan.dto;

public class PersonDTO {
    private Long personId;
    private Long bankId ;
    private String accountHolder;
    private String accountNumber;
    private String branchCode;
    private String email;
    private String phoneExtension;
    private String phoneNumber;

    public PersonDTO(Long personId, Long bankId, String accountHolder, String accountNumber, String branchCode, String email, String phoneExtension, String phoneNumber) {
        this.personId = personId;
        this.bankId = bankId;
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.branchCode = branchCode;
        this.email = email;
        this.phoneExtension = phoneExtension;
        this.phoneNumber = phoneNumber;
    }

    public PersonDTO() {
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(String phoneExtension) {
        this.phoneExtension = phoneExtension;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
