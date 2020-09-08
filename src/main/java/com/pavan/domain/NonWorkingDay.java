package com.pavan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A NonWorkingDay.
 */
@Entity
@Table(name = "non_working_day")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NonWorkingDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "non_working_day")
    private Instant nonWorkingDay;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JsonIgnoreProperties(value = "nonWorkingDays", allowSetters = true)
    private Institution institution;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getNonWorkingDay() {
        return nonWorkingDay;
    }

    public NonWorkingDay nonWorkingDay(Instant nonWorkingDay) {
        this.nonWorkingDay = nonWorkingDay;
        return this;
    }

    public void setNonWorkingDay(Instant nonWorkingDay) {
        this.nonWorkingDay = nonWorkingDay;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public NonWorkingDay deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Institution getInstitution() {
        return institution;
    }

    public NonWorkingDay institution(Institution institution) {
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
        if (!(o instanceof NonWorkingDay)) {
            return false;
        }
        return id != null && id.equals(((NonWorkingDay) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NonWorkingDay{" +
            "id=" + getId() +
            ", nonWorkingDay='" + getNonWorkingDay() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
