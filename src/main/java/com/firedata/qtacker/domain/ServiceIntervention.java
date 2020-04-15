package com.firedata.qtacker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A ServiceIntervention.
 */
@Entity
@Table(name = "service_intervention")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "serviceintervention")
public class ServiceIntervention implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_time")
    private Instant dateTime;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("serviceInterventions")
    private Device device;

    @ManyToOne
    @JsonIgnoreProperties("serviceInterventions")
    private CompanyExtern companyExtern;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public ServiceIntervention dateTime(Instant dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Double getPrice() {
        return price;
    }

    public ServiceIntervention price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public ServiceIntervention description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Device getDevice() {
        return device;
    }

    public ServiceIntervention device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public CompanyExtern getCompanyExtern() {
        return companyExtern;
    }

    public ServiceIntervention companyExtern(CompanyExtern companyExtern) {
        this.companyExtern = companyExtern;
        return this;
    }

    public void setCompanyExtern(CompanyExtern companyExtern) {
        this.companyExtern = companyExtern;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceIntervention)) {
            return false;
        }
        return id != null && id.equals(((ServiceIntervention) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceIntervention{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
