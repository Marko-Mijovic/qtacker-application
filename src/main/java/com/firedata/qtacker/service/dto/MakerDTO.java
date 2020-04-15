package com.firedata.qtacker.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.firedata.qtacker.domain.Maker} entity.
 */
public class MakerDTO implements Serializable {
    
    private Long id;

    private String makerName;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MakerDTO makerDTO = (MakerDTO) o;
        if (makerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), makerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MakerDTO{" +
            "id=" + getId() +
            ", makerName='" + getMakerName() + "'" +
            "}";
    }
}
