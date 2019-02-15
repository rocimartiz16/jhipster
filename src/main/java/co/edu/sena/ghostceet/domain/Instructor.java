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
 * A Instructor.
 */
@Entity
@Table(name = "instructor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Instructor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "instructor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VinculacionInstructor> vinculacionInstructors = new HashSet<>();
    @OneToMany(mappedBy = "instructor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AreaInstructor> areaInstructors = new HashSet<>();
    @OneToMany(mappedBy = "instructor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DisponibilidadCompetencias> disponibilidadCompetencias = new HashSet<>();
    @OneToMany(mappedBy = "instructor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DisponibilidadHoraria> disponibilidadHorarias = new HashSet<>();
    @OneToMany(mappedBy = "instructor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Horario> horarios = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("instructors")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public Instructor estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<VinculacionInstructor> getVinculacionInstructors() {
        return vinculacionInstructors;
    }

    public Instructor vinculacionInstructors(Set<VinculacionInstructor> vinculacionInstructors) {
        this.vinculacionInstructors = vinculacionInstructors;
        return this;
    }

    public Instructor addVinculacionInstructor(VinculacionInstructor vinculacionInstructor) {
        this.vinculacionInstructors.add(vinculacionInstructor);
        vinculacionInstructor.setInstructor(this);
        return this;
    }

    public Instructor removeVinculacionInstructor(VinculacionInstructor vinculacionInstructor) {
        this.vinculacionInstructors.remove(vinculacionInstructor);
        vinculacionInstructor.setInstructor(null);
        return this;
    }

    public void setVinculacionInstructors(Set<VinculacionInstructor> vinculacionInstructors) {
        this.vinculacionInstructors = vinculacionInstructors;
    }

    public Set<AreaInstructor> getAreaInstructors() {
        return areaInstructors;
    }

    public Instructor areaInstructors(Set<AreaInstructor> areaInstructors) {
        this.areaInstructors = areaInstructors;
        return this;
    }

    public Instructor addAreaInstructor(AreaInstructor areaInstructor) {
        this.areaInstructors.add(areaInstructor);
        areaInstructor.setInstructor(this);
        return this;
    }

    public Instructor removeAreaInstructor(AreaInstructor areaInstructor) {
        this.areaInstructors.remove(areaInstructor);
        areaInstructor.setInstructor(null);
        return this;
    }

    public void setAreaInstructors(Set<AreaInstructor> areaInstructors) {
        this.areaInstructors = areaInstructors;
    }

    public Set<DisponibilidadCompetencias> getDisponibilidadCompetencias() {
        return disponibilidadCompetencias;
    }

    public Instructor disponibilidadCompetencias(Set<DisponibilidadCompetencias> disponibilidadCompetencias) {
        this.disponibilidadCompetencias = disponibilidadCompetencias;
        return this;
    }

    public Instructor addDisponibilidadCompetencias(DisponibilidadCompetencias disponibilidadCompetencias) {
        this.disponibilidadCompetencias.add(disponibilidadCompetencias);
        disponibilidadCompetencias.setInstructor(this);
        return this;
    }

    public Instructor removeDisponibilidadCompetencias(DisponibilidadCompetencias disponibilidadCompetencias) {
        this.disponibilidadCompetencias.remove(disponibilidadCompetencias);
        disponibilidadCompetencias.setInstructor(null);
        return this;
    }

    public void setDisponibilidadCompetencias(Set<DisponibilidadCompetencias> disponibilidadCompetencias) {
        this.disponibilidadCompetencias = disponibilidadCompetencias;
    }

    public Set<DisponibilidadHoraria> getDisponibilidadHorarias() {
        return disponibilidadHorarias;
    }

    public Instructor disponibilidadHorarias(Set<DisponibilidadHoraria> disponibilidadHorarias) {
        this.disponibilidadHorarias = disponibilidadHorarias;
        return this;
    }

    public Instructor addDisponibilidadHoraria(DisponibilidadHoraria disponibilidadHoraria) {
        this.disponibilidadHorarias.add(disponibilidadHoraria);
        disponibilidadHoraria.setInstructor(this);
        return this;
    }

    public Instructor removeDisponibilidadHoraria(DisponibilidadHoraria disponibilidadHoraria) {
        this.disponibilidadHorarias.remove(disponibilidadHoraria);
        disponibilidadHoraria.setInstructor(null);
        return this;
    }

    public void setDisponibilidadHorarias(Set<DisponibilidadHoraria> disponibilidadHorarias) {
        this.disponibilidadHorarias = disponibilidadHorarias;
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public Instructor horarios(Set<Horario> horarios) {
        this.horarios = horarios;
        return this;
    }

    public Instructor addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setInstructor(this);
        return this;
    }

    public Instructor removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setInstructor(null);
        return this;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Instructor cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        Instructor instructor = (Instructor) o;
        if (instructor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instructor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Instructor{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
