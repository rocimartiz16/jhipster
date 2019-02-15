package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PlaneacionTrimestre.
 */
@Entity
@Table(name = "planeacion_trimestre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlaneacionTrimestre implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "planeacionTrimestre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlaneacionActividad> planeacionActividads = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("planeacionTrimestre5S")
    private Trimestre trimestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PlaneacionActividad> getPlaneacionActividads() {
        return planeacionActividads;
    }

    public PlaneacionTrimestre planeacionActividads(Set<PlaneacionActividad> planeacionActividads) {
        this.planeacionActividads = planeacionActividads;
        return this;
    }

    public PlaneacionTrimestre addPlaneacionActividad(PlaneacionActividad planeacionActividad) {
        this.planeacionActividads.add(planeacionActividad);
        planeacionActividad.setPlaneacionTrimestre(this);
        return this;
    }

    public PlaneacionTrimestre removePlaneacionActividad(PlaneacionActividad planeacionActividad) {
        this.planeacionActividads.remove(planeacionActividad);
        planeacionActividad.setPlaneacionTrimestre(null);
        return this;
    }

    public void setPlaneacionActividads(Set<PlaneacionActividad> planeacionActividads) {
        this.planeacionActividads = planeacionActividads;
    }

    public Trimestre getTrimestre() {
        return trimestre;
    }

    public PlaneacionTrimestre trimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
        return this;
    }

    public void setTrimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlaneacionTrimestre planeacionTrimestre = (PlaneacionTrimestre) o;
        if (planeacionTrimestre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planeacionTrimestre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlaneacionTrimestre{" +
            "id=" + getId() +
            "}";
    }
}
