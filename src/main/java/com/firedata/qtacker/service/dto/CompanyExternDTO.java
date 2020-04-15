package com.firedata.qtacker.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.firedata.qtacker.domain.CompanyExtern} entity.
 */
public class CompanyExternDTO implements Serializable {
    
    private Long id;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyExternDTO companyExternDTO = (CompanyExternDTO) o;
        if (companyExternDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyExternDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyExternDTO{" +
            "id=" + getId() +
            "}";
    }
}
