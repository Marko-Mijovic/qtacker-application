package com.firedata.qtacker.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.firedata.qtacker.domain.Model} entity.
 */
public class ModelDTO implements Serializable {
    
    private Long id;

    private String modelName;


    private Long makerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getMakerId() {
        return makerId;
    }

    public void setMakerId(Long makerId) {
        this.makerId = makerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModelDTO modelDTO = (ModelDTO) o;
        if (modelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModelDTO{" +
            "id=" + getId() +
            ", modelName='" + getModelName() + "'" +
            ", makerId=" + getMakerId() +
            "}";
    }
}
