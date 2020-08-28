package com.pavan.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A LegalEntity.
 */
@Entity
@Table(name = "legal_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LegalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "commercial_name")
    private String commercialName;

    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "title")
    private String title;

    @Column(name = "date_of_start")
    private Instant dateOfStart;

    @Column(name = "business_closed")
    private Boolean businessClosed;

    @Column(name = "business_area")
    private String businessArea;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToOne
    @JoinColumn(unique = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public LegalEntity commercialName(String commercialName) {
        this.commercialName = commercialName;
        return this;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public LegalEntity taxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
        return this;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getTitle() {
        return title;
    }

    public LegalEntity title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getDateOfStart() {
        return dateOfStart;
    }

    public LegalEntity dateOfStart(Instant dateOfStart) {
        this.dateOfStart = dateOfStart;
        return this;
    }

    public void setDateOfStart(Instant dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public Boolean isBusinessClosed() {
        return businessClosed;
    }

    public LegalEntity businessClosed(Boolean businessClosed) {
        this.businessClosed = businessClosed;
        return this;
    }

    public void setBusinessClosed(Boolean businessClosed) {
        this.businessClosed = businessClosed;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public LegalEntity businessArea(String businessArea) {
        this.businessArea = businessArea;
        return this;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public LegalEntity deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LegalEntity customer(Customer customer) {
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
        if (!(o instanceof LegalEntity)) {
            return false;
        }
        return id != null && id.equals(((LegalEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LegalEntity{" +
            "id=" + getId() +
            ", commercialName='" + getCommercialName() + "'" +
            ", taxNumber='" + getTaxNumber() + "'" +
            ", title='" + getTitle() + "'" +
            ", dateOfStart='" + getDateOfStart() + "'" +
            ", businessClosed='" + isBusinessClosed() + "'" +
            ", businessArea='" + getBusinessArea() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
