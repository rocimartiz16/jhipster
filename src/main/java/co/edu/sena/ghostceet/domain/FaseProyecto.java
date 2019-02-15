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
 * A FaseProyecto.
 */
@Entity
@Table(name = "fase_proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FaseProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nombre_fase", length = 20, nullable = false)
    private String nombreFase;

    @NotNull
    @Size(max = 40)
    @Column(name = "estado_fase", length = 40, nullable = false)
    private String estadoFase;

    @OneToMany(mappedBy = "faseProyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActividadProyecto> actividadProyecto7S = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("faseProyecto6S")
    private Proyecto proyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFase() {
        return nombreFase;
    }

    public FaseProyecto nombreFase(String nombreFase) {
        this.nombreFase = nombreFase;
        return this;
    }

    public void setNombreFase(String nombreFase) {
        this.nombreFase = nombreFase;
    }

    public String getEstadoFase() {
        return estadoFase;
    }

    public FaseProyecto estadoFase(String estadoFase) {
        this.estadoFase = estadoFase;
        return this;
    }

    public void setEstadoFase(String estadoFase) {
        this.estadoFase = estadoFase;
    }

    public Set<ActividadProyecto> getActividadProyecto7S() {
        return actividadProyecto7S;
    }

    public FaseProyecto actividadProyecto7S(Set<ActividadProyecto> actividadProyectos) {
        this.actividadProyecto7S = actividadProyectos;
        return this;
    }

    public FaseProyecto addActividadProyecto7(ActividadProyecto actividadProyecto) {
        this.actividadProyecto7S.add(actividadProyecto);
        actividadProyecto.setFaseProyecto(this);
        return this;
    }

    public FaseProyecto removeActividadProyecto7(ActividadProyecto actividadProyecto) {
        this.actividadProyecto7S.remove(actividadProyecto);
        actividadProyecto.setFaseProyecto(null);
        return this;
    }

    public void setActividadProyecto7S(Set<ActividadProyecto> actividadProyectos) {
        this.actividadProyecto7S = actividadProyectos;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public FaseProyecto proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
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
        FaseProyecto faseProyecto = (FaseProyecto) o;
        if (faseProyecto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faseProyecto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FaseProyecto{" +
            "id=" + getId() +
            ", nombreFase='" + getNombreFase() + "'" +
            ", estadoFase='" + getEstadoFase() + "'" +
            "}";
    }
}
