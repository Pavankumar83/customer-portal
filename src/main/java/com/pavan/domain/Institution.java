package com.pavan.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Institution.
 */
@Entity
@Table(name = "institution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "url")
    private String url;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "institution")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BankInfo> bankInfos = new HashSet<>();

    @OneToMany(mappedBy = "institution")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<NonWorkingDay> nonWorkingDays = new HashSet<>();

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

    public Institution name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTradeName() {
        return tradeName;
    }

    public Institution tradeName(String tradeName) {
        this.tradeName = tradeName;
        return this;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public Institution taxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
        return this;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getUrl() {
        return url;
    }

    public Institution url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Institution deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<BankInfo> getBankInfos() {
        return bankInfos;
    }

    public Institution bankInfos(Set<BankInfo> bankInfos) {
        this.bankInfos = bankInfos;
        return this;
    }

    public Institution addBankInfo(BankInfo bankInfo) {
        this.bankInfos.add(bankInfo);
        bankInfo.setInstitution(this);
        return this;
    }

    public Institution removeBankInfo(BankInfo bankInfo) {
        this.bankInfos.remove(bankInfo);
        bankInfo.setInstitution(null);
        return this;
    }

    public void setBankInfos(Set<BankInfo> bankInfos) {
        this.bankInfos = bankInfos;
    }

    public Set<NonWorkingDay> getNonWorkingDays() {
        return nonWorkingDays;
    }

    public Institution nonWorkingDays(Set<NonWorkingDay> nonWorkingDays) {
        this.nonWorkingDays = nonWorkingDays;
        return this;
    }

    public Institution addNonWorkingDay(NonWorkingDay nonWorkingDay) {
        this.nonWorkingDays.add(nonWorkingDay);
        nonWorkingDay.setInstitution(this);
        return this;
    }

    public Institution removeNonWorkingDay(NonWorkingDay nonWorkingDay) {
        this.nonWorkingDays.remove(nonWorkingDay);
        nonWorkingDay.setInstitution(null);
        return this;
    }

    public void setNonWorkingDays(Set<NonWorkingDay> nonWorkingDays) {
        this.nonWorkingDays = nonWorkingDays;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Institution)) {
            return false;
        }
        return id != null && id.equals(((Institution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Institution{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", tradeName='" + getTradeName() + "'" +
            ", taxNumber='" + getTaxNumber() + "'" +
            ", url='" + getUrl() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
