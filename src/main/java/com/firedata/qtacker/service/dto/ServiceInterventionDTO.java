package com.firedata.qtacker.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.firedata.qtacker.domain.ServiceIntervention} entity.
 */
public class ServiceInterventionDTO implements Serializable {
    
    private Long id;

    private Instant dateTime;

    private Double price;

    private String description;


    private Long deviceId;

    private Long companyExternId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getCompanyExternId() {
        return companyExternId;
    }

    public void setCompanyExternId(Long companyExternId) {
        this.companyExternId = companyExternId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceInterventionDTO serviceInterventionDTO = (ServiceInterventionDTO) o;
        if (serviceInterventionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceInterventionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceInterventionDTO{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", deviceId=" + getDeviceId() +
            ", companyExternId=" + getCompanyExternId() +
            "}";
    }
}
