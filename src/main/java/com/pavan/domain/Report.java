package com.pavan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.pavan.domain.enumeration.ReportType;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ReportType type;

    @NotNull
    @Column(name = "start_period", nullable = false)
    private Instant startPeriod;

    @NotNull
    @Column(name = "end_period", nullable = false)
    private Instant endPeriod;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "generated_report_name", nullable = false)
    private String generatedReportName;

    @NotNull
    @Column(name = "generated_aif_name", nullable = false)
    private String generatedAIFName;

    @NotNull
    @Column(name = "generated_report_location", nullable = false)
    private String generatedReportLocation;

    @Column(name = "generated_aif_location")
    private String generatedAIFLocation;

    @ManyToOne
    @JsonIgnoreProperties(value = "reports", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportType getType() {
        return type;
    }

    public Report type(ReportType type) {
        this.type = type;
        return this;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Instant getStartPeriod() {
        return startPeriod;
    }

    public Report startPeriod(Instant startPeriod) {
        this.startPeriod = startPeriod;
        return this;
    }

    public void setStartPeriod(Instant startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Instant getEndPeriod() {
        return endPeriod;
    }

    public Report endPeriod(Instant endPeriod) {
        this.endPeriod = endPeriod;
        return this;
    }

    public void setEndPeriod(Instant endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getName() {
        return name;
    }

    public Report name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneratedReportName() {
        return generatedReportName;
    }

    public Report generatedReportName(String generatedReportName) {
        this.generatedReportName = generatedReportName;
        return this;
    }

    public void setGeneratedReportName(String generatedReportName) {
        this.generatedReportName = generatedReportName;
    }

    public String getGeneratedAIFName() {
        return generatedAIFName;
    }

    public Report generatedAIFName(String generatedAIFName) {
        this.generatedAIFName = generatedAIFName;
        return this;
    }

    public void setGeneratedAIFName(String generatedAIFName) {
        this.generatedAIFName = generatedAIFName;
    }

    public String getGeneratedReportLocation() {
        return generatedReportLocation;
    }

    public Report generatedReportLocation(String generatedReportLocation) {
        this.generatedReportLocation = generatedReportLocation;
        return this;
    }

    public void setGeneratedReportLocation(String generatedReportLocation) {
        this.generatedReportLocation = generatedReportLocation;
    }

    public String getGeneratedAIFLocation() {
        return generatedAIFLocation;
    }

    public Report generatedAIFLocation(String generatedAIFLocation) {
        this.generatedAIFLocation = generatedAIFLocation;
        return this;
    }

    public void setGeneratedAIFLocation(String generatedAIFLocation) {
        this.generatedAIFLocation = generatedAIFLocation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Report customer(Customer customer) {
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
        if (!(o instanceof Report)) {
            return false;
        }
        return id != null && id.equals(((Report) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", startPeriod='" + getStartPeriod() + "'" +
            ", endPeriod='" + getEndPeriod() + "'" +
            ", name='" + getName() + "'" +
            ", generatedReportName='" + getGeneratedReportName() + "'" +
            ", generatedAIFName='" + getGeneratedAIFName() + "'" +
            ", generatedReportLocation='" + getGeneratedReportLocation() + "'" +
            ", generatedAIFLocation='" + getGeneratedAIFLocation() + "'" +
            "}";
    }
}
