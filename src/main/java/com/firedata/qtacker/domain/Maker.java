package com.firedata.qtacker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Maker.
 */
@Entity
@Table(name = "maker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "maker")
public class Maker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "maker_name")
    private String makerName;

    @OneToMany(mappedBy = "maker")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Model> models = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMakerName() {
        return makerName;
    }

    public Maker makerName(String makerName) {
        this.makerName = makerName;
        return this;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public Set<Model> getModels() {
        return models;
    }

    public Maker models(Set<Model> models) {
        this.models = models;
        return this;
    }

    public Maker addModels(Model model) {
        this.models.add(model);
        model.setMaker(this);
        return this;
    }

    public Maker removeModels(Model model) {
        this.models.remove(model);
        model.setMaker(null);
        return this;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Maker)) {
            return false;
        }
        return id != null && id.equals(((Maker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Maker{" +
            "id=" + getId() +
            ", makerName='" + getMakerName() + "'" +
            "}";
    }
}
