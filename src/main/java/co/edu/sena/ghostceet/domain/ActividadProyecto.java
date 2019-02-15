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
 * A ActividadProyecto.
 */
@Entity
@Table(name = "actividad_proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActividadProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_actividad", nullable = false)
    private Integer numeroActividad;

    @NotNull
    @Size(max = 300)
    @Column(name = "nombre_actividad", length = 300, nullable = false)
    private String nombreActividad;

    @OneToMany(mappedBy = "actividadProyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlaneacionActividad> planeacionActividads = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("actividadProyecto7S")
    private FaseProyecto faseProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroActividad() {
        return numeroActividad;
    }

    public ActividadProyecto numeroActividad(Integer numeroActividad) {
        this.numeroActividad = numeroActividad;
        return this;
    }

    public void setNumeroActividad(Integer numeroActividad) {
        this.numeroActividad = numeroActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public ActividadProyecto nombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
        return this;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public Set<PlaneacionActividad> getPlaneacionActividads() {
        return planeacionActividads;
    }

    public ActividadProyecto planeacionActividads(Set<PlaneacionActividad> planeacionActividads) {
        this.planeacionActividads = planeacionActividads;
        return this;
    }

    public ActividadProyecto addPlaneacionActividad(PlaneacionActividad planeacionActividad) {
        this.planeacionActividads.add(planeacionActividad);
        planeacionActividad.setActividadProyecto(this);
        return this;
    }

    public ActividadProyecto removePlaneacionActividad(PlaneacionActividad planeacionActividad) {
        this.planeacionActividads.remove(planeacionActividad);
        planeacionActividad.setActividadProyecto(null);
        return this;
    }

    public void setPlaneacionActividads(Set<PlaneacionActividad> planeacionActividads) {
        this.planeacionActividads = planeacionActividads;
    }

    public FaseProyecto getFaseProyecto() {
        return faseProyecto;
    }

    public ActividadProyecto faseProyecto(FaseProyecto faseProyecto) {
        this.faseProyecto = faseProyecto;
        return this;
    }

    public void setFaseProyecto(FaseProyecto faseProyecto) {
        this.faseProyecto = faseProyecto;
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
        ActividadProyecto actividadProyecto = (ActividadProyecto) o;
        if (actividadProyecto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actividadProyecto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActividadProyecto{" +
            "id=" + getId() +
            ", numeroActividad=" + getNumeroActividad() +
            ", nombreActividad='" + getNombreActividad() + "'" +
            "}";
    }
}
