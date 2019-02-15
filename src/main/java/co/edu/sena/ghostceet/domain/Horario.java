package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Horario.
 */
@Entity
@Table(name = "horario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private ZonedDateTime horaInicio;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private ZonedDateTime horaFin;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("modalidads")
    private Modalidad modalidad;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dias")
    private Dia dia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("fichaTrimestres")
    private FichaTrimestre fichaTrimestre;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ambientes")
    private Ambiente ambiente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("instructors")
    private Instructor instructor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("horarios")
    private VersionHorario versionHorario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getHoraInicio() {
        return horaInicio;
    }

    public Horario horaInicio(ZonedDateTime horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(ZonedDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public ZonedDateTime getHoraFin() {
        return horaFin;
    }

    public Horario horaFin(ZonedDateTime horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public void setHoraFin(ZonedDateTime horaFin) {
        this.horaFin = horaFin;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public Horario modalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Dia getDia() {
        return dia;
    }

    public Horario dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public FichaTrimestre getFichaTrimestre() {
        return fichaTrimestre;
    }

    public Horario fichaTrimestre(FichaTrimestre fichaTrimestre) {
        this.fichaTrimestre = fichaTrimestre;
        return this;
    }

    public void setFichaTrimestre(FichaTrimestre fichaTrimestre) {
        this.fichaTrimestre = fichaTrimestre;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public Horario ambiente(Ambiente ambiente) {
        this.ambiente = ambiente;
        return this;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Horario instructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public VersionHorario getVersionHorario() {
        return versionHorario;
    }

    public Horario versionHorario(VersionHorario versionHorario) {
        this.versionHorario = versionHorario;
        return this;
    }

    public void setVersionHorario(VersionHorario versionHorario) {
        this.versionHorario = versionHorario;
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
        Horario horario = (Horario) o;
        if (horario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Horario{" +
            "id=" + getId() +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFin='" + getHoraFin() + "'" +
            "}";
    }
}
