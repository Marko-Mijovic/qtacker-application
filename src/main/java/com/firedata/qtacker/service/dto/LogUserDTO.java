package com.firedata.qtacker.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.firedata.qtacker.domain.LogUser} entity.
 */
public class LogUserDTO implements Serializable {
    
    private Long id;

    private Instant dateTimeLog;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTimeLog() {
        return dateTimeLog;
    }

    public void setDateTimeLog(Instant dateTimeLog) {
        this.dateTimeLog = dateTimeLog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogUserDTO logUserDTO = (LogUserDTO) o;
        if (logUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogUserDTO{" +
            "id=" + getId() +
            ", dateTimeLog='" + getDateTimeLog() + "'" +
            "}";
    }
}
