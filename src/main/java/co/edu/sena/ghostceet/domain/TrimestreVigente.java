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

import co.edu.sena.ghostceet.domain.enumeration.Estado;

/**
 * A TrimestreVigente.
 */
@Entity
@Table(name = "trimestre_vigente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrimestreVigente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "anio", nullable = false)
    private Integer anio;

    @NotNull
    @Column(name = "trimestre_programado", nullable = false)
    private Integer trimestreProgramado;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private ZonedDateTime fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private ZonedDateTime fechaFin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "versionTrimestre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VersionHorario> versionHorarios = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("trimestreVigentes")
    private Anio anio1;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnio() {
        return anio;
    }

    public TrimestreVigente anio(Integer anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getTrimestreProgramado() {
        return trimestreProgramado;
    }

    public TrimestreVigente trimestreProgramado(Integer trimestreProgramado) {
        this.trimestreProgramado = trimestreProgramado;
        return this;
    }

    public void setTrimestreProgramado(Integer trimestreProgramado) {
        this.trimestreProgramado = trimestreProgramado;
    }

    public ZonedDateTime getFechaInicio() {
        return fechaInicio;
    }

    public TrimestreVigente fechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public TrimestreVigente fechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Estado getEstado() {
        return estado;
    }

    public TrimestreVigente estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<VersionHorario> getVersionHorarios() {
        return versionHorarios;
    }

    public TrimestreVigente versionHorarios(Set<VersionHorario> versionHorarios) {
        this.versionHorarios = versionHorarios;
        return this;
    }

    public TrimestreVigente addVersionHorario(VersionHorario versionHorario) {
        this.versionHorarios.add(versionHorario);
        versionHorario.setVersionTrimestre(this);
        return this;
    }

    public TrimestreVigente removeVersionHorario(VersionHorario versionHorario) {
        this.versionHorarios.remove(versionHorario);
        versionHorario.setVersionTrimestre(null);
        return this;
    }

    public void setVersionHorarios(Set<VersionHorario> versionHorarios) {
        this.versionHorarios = versionHorarios;
    }

    public Anio getAnio1() {
        return anio1;
    }

    public TrimestreVigente anio1(Anio anio) {
        this.anio1 = anio;
        return this;
    }

    public void setAnio1(Anio anio) {
        this.anio1 = anio;
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
        TrimestreVigente trimestreVigente = (TrimestreVigente) o;
        if (trimestreVigente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trimestreVigente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrimestreVigente{" +
            "id=" + getId() +
            ", anio=" + getAnio() +
            ", trimestreProgramado=" + getTrimestreProgramado() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
