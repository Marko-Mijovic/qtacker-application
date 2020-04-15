package com.firedata.qtacker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A LogUser.
 */
@Entity
@Table(name = "log_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "loguser")
public class LogUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_time_log")
    private Instant dateTimeLog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTimeLog() {
        return dateTimeLog;
    }

    public LogUser dateTimeLog(Instant dateTimeLog) {
        this.dateTimeLog = dateTimeLog;
        return this;
    }

    public void setDateTimeLog(Instant dateTimeLog) {
        this.dateTimeLog = dateTimeLog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogUser)) {
            return false;
        }
        return id != null && id.equals(((LogUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LogUser{" +
            "id=" + getId() +
            ", dateTimeLog='" + getDateTimeLog() + "'" +
            "}";
    }
}
