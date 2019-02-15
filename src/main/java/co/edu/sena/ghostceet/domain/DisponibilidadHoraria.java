package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DisponibilidadHoraria.
 */
@Entity
@Table(name = "disponibilidad_horaria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DisponibilidadHoraria implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("disponibilidadHorarias")
    private VinculacionInstructor vinculacionInstructor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("jornadaInstructors")
    private JornadaInstructor jornada;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("instructors")
    private Instructor instructor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VinculacionInstructor getVinculacionInstructor() {
        return vinculacionInstructor;
    }

    public DisponibilidadHoraria vinculacionInstructor(VinculacionInstructor vinculacionInstructor) {
        this.vinculacionInstructor = vinculacionInstructor;
        return this;
    }

    public void setVinculacionInstructor(VinculacionInstructor vinculacionInstructor) {
        this.vinculacionInstructor = vinculacionInstructor;
    }

    public JornadaInstructor getJornada() {
        return jornada;
    }

    public DisponibilidadHoraria jornada(JornadaInstructor jornadaInstructor) {
        this.jornada = jornadaInstructor;
        return this;
    }

    public void setJornada(JornadaInstructor jornadaInstructor) {
        this.jornada = jornadaInstructor;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public DisponibilidadHoraria instructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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
        DisponibilidadHoraria disponibilidadHoraria = (DisponibilidadHoraria) o;
        if (disponibilidadHoraria.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disponibilidadHoraria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DisponibilidadHoraria{" +
            "id=" + getId() +
            "}";
    }
}
