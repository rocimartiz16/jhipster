package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A VinculacionInstructor.
 */
@Entity
@Table(name = "vinculacion_instructor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VinculacionInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private ZonedDateTime fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private ZonedDateTime fechaFin;

    @OneToMany(mappedBy = "vinculacionInstructor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DisponibilidadCompetencias> disponibilidadCompetencias = new HashSet<>();
    @OneToMany(mappedBy = "vinculacionInstructor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DisponibilidadHoraria> disponibilidadHorarias = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vinculacionInstructors")
    private Instructor instructor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vinculacionInstructors")
    private Anio anio1;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vinculacionInstructors")
    private Vinculacion vinculacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaInicio() {
        return fechaInicio;
    }

    public VinculacionInstructor fechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public VinculacionInstructor fechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Set<DisponibilidadCompetencias> getDisponibilidadCompetencias() {
        return disponibilidadCompetencias;
    }

    public VinculacionInstructor disponibilidadCompetencias(Set<DisponibilidadCompetencias> disponibilidadCompetencias) {
        this.disponibilidadCompetencias = disponibilidadCompetencias;
        return this;
    }

    public VinculacionInstructor addDisponibilidadCompetencias(DisponibilidadCompetencias disponibilidadCompetencias) {
        this.disponibilidadCompetencias.add(disponibilidadCompetencias);
        disponibilidadCompetencias.setVinculacionInstructor(this);
        return this;
    }

    public VinculacionInstructor removeDisponibilidadCompetencias(DisponibilidadCompetencias disponibilidadCompetencias) {
        this.disponibilidadCompetencias.remove(disponibilidadCompetencias);
        disponibilidadCompetencias.setVinculacionInstructor(null);
        return this;
    }

    public void setDisponibilidadCompetencias(Set<DisponibilidadCompetencias> disponibilidadCompetencias) {
        this.disponibilidadCompetencias = disponibilidadCompetencias;
    }

    public Set<DisponibilidadHoraria> getDisponibilidadHorarias() {
        return disponibilidadHorarias;
    }

    public VinculacionInstructor disponibilidadHorarias(Set<DisponibilidadHoraria> disponibilidadHorarias) {
        this.disponibilidadHorarias = disponibilidadHorarias;
        return this;
    }

    public VinculacionInstructor addDisponibilidadHoraria(DisponibilidadHoraria disponibilidadHoraria) {
        this.disponibilidadHorarias.add(disponibilidadHoraria);
        disponibilidadHoraria.setVinculacionInstructor(this);
        return this;
    }

    public VinculacionInstructor removeDisponibilidadHoraria(DisponibilidadHoraria disponibilidadHoraria) {
        this.disponibilidadHorarias.remove(disponibilidadHoraria);
        disponibilidadHoraria.setVinculacionInstructor(null);
        return this;
    }

    public void setDisponibilidadHorarias(Set<DisponibilidadHoraria> disponibilidadHorarias) {
        this.disponibilidadHorarias = disponibilidadHorarias;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public VinculacionInstructor instructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Anio getAnio1() {
        return anio1;
    }

    public VinculacionInstructor anio1(Anio anio) {
        this.anio1 = anio;
        return this;
    }

    public void setAnio1(Anio anio) {
        this.anio1 = anio;
    }

    public Vinculacion getVinculacion() {
        return vinculacion;
    }

    public VinculacionInstructor vinculacion(Vinculacion vinculacion) {
        this.vinculacion = vinculacion;
        return this;
    }

    public void setVinculacion(Vinculacion vinculacion) {
        this.vinculacion = vinculacion;
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
        VinculacionInstructor vinculacionInstructor = (VinculacionInstructor) o;
        if (vinculacionInstructor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vinculacionInstructor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VinculacionInstructor{" +
            "id=" + getId() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}
