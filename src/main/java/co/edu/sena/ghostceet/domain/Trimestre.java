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

import co.edu.sena.ghostceet.domain.enumeration.Estado;

/**
 * A Trimestre.
 */
@Entity
@Table(name = "trimestre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trimestre implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nombre_trimestre", length = 20, nullable = false)
    private String nombreTrimestre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "trimestre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlaneacionTrimestre> planeacionTrimestre5S = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("trimestre4S")
    private Jornada jornada;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTrimestre() {
        return nombreTrimestre;
    }

    public Trimestre nombreTrimestre(String nombreTrimestre) {
        this.nombreTrimestre = nombreTrimestre;
        return this;
    }

    public void setNombreTrimestre(String nombreTrimestre) {
        this.nombreTrimestre = nombreTrimestre;
    }

    public Estado getEstado() {
        return estado;
    }

    public Trimestre estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<PlaneacionTrimestre> getPlaneacionTrimestre5S() {
        return planeacionTrimestre5S;
    }

    public Trimestre planeacionTrimestre5S(Set<PlaneacionTrimestre> planeacionTrimestres) {
        this.planeacionTrimestre5S = planeacionTrimestres;
        return this;
    }

    public Trimestre addPlaneacionTrimestre5(PlaneacionTrimestre planeacionTrimestre) {
        this.planeacionTrimestre5S.add(planeacionTrimestre);
        planeacionTrimestre.setTrimestre(this);
        return this;
    }

    public Trimestre removePlaneacionTrimestre5(PlaneacionTrimestre planeacionTrimestre) {
        this.planeacionTrimestre5S.remove(planeacionTrimestre);
        planeacionTrimestre.setTrimestre(null);
        return this;
    }

    public void setPlaneacionTrimestre5S(Set<PlaneacionTrimestre> planeacionTrimestres) {
        this.planeacionTrimestre5S = planeacionTrimestres;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public Trimestre jornada(Jornada jornada) {
        this.jornada = jornada;
        return this;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
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
        Trimestre trimestre = (Trimestre) o;
        if (trimestre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trimestre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trimestre{" +
            "id=" + getId() +
            ", nombreTrimestre='" + getNombreTrimestre() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
