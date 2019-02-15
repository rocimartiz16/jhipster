package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DisponibilidadCompetencias.
 */
@Entity
@Table(name = "disponibilidad_competencias")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DisponibilidadCompetencias implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("disponibilidadCompetencias")
    private Instructor instructor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("disponibilidadCompetencias")
    private VinculacionInstructor vinculacionInstructor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public DisponibilidadCompetencias instructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public VinculacionInstructor getVinculacionInstructor() {
        return vinculacionInstructor;
    }

    public DisponibilidadCompetencias vinculacionInstructor(VinculacionInstructor vinculacionInstructor) {
        this.vinculacionInstructor = vinculacionInstructor;
        return this;
    }

    public void setVinculacionInstructor(VinculacionInstructor vinculacionInstructor) {
        this.vinculacionInstructor = vinculacionInstructor;
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
        DisponibilidadCompetencias disponibilidadCompetencias = (DisponibilidadCompetencias) o;
        if (disponibilidadCompetencias.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disponibilidadCompetencias.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DisponibilidadCompetencias{" +
            "id=" + getId() +
            "}";
    }
}
