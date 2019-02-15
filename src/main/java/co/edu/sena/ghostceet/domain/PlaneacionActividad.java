package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PlaneacionActividad.
 */
@Entity
@Table(name = "planeacion_actividad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlaneacionActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("planeacionActividads")
    private PlaneacionTrimestre planeacionTrimestre;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("planeacionActividads")
    private ActividadProyecto actividadProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlaneacionTrimestre getPlaneacionTrimestre() {
        return planeacionTrimestre;
    }

    public PlaneacionActividad planeacionTrimestre(PlaneacionTrimestre planeacionTrimestre) {
        this.planeacionTrimestre = planeacionTrimestre;
        return this;
    }

    public void setPlaneacionTrimestre(PlaneacionTrimestre planeacionTrimestre) {
        this.planeacionTrimestre = planeacionTrimestre;
    }

    public ActividadProyecto getActividadProyecto() {
        return actividadProyecto;
    }

    public PlaneacionActividad actividadProyecto(ActividadProyecto actividadProyecto) {
        this.actividadProyecto = actividadProyecto;
        return this;
    }

    public void setActividadProyecto(ActividadProyecto actividadProyecto) {
        this.actividadProyecto = actividadProyecto;
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
        PlaneacionActividad planeacionActividad = (PlaneacionActividad) o;
        if (planeacionActividad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planeacionActividad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlaneacionActividad{" +
            "id=" + getId() +
            "}";
    }
}
