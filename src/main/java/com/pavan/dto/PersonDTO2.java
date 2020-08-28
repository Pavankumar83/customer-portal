package com.pavan.dto;

import com.pavan.domain.*;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO2 {
    private Person person;
    private List<BankInfo> bankAccounts;
    private List<CustomerEmail> emails;
    private List<CustomerPhone> phones;

    public PersonDTO2() {
        this.bankAccounts =new ArrayList<>();
        this.emails =new ArrayList<>();
        this.phones =new ArrayList<>();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<BankInfo> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankInfo> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<CustomerEmail> getEmails() {
        return emails;
    }

    public void setEmails(List<CustomerEmail> emails) {
        this.emails = emails;
    }

    public List<CustomerPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<CustomerPhone> phones) {
        this.phones = phones;
    }
}
