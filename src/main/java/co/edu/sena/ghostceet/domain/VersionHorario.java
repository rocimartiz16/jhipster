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
 * A VersionHorario.
 */
@Entity
@Table(name = "version_horario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VersionHorario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_version", nullable = false)
    private Integer numeroVersion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @NotNull
    @Column(name = "trimestre_vigente_id", nullable = false)
    private Integer trimestreVigenteId;

    @OneToMany(mappedBy = "versionHorario")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Horario> horarios = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("trimestreVigentes")
    private TrimestreVigente versionTrimestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroVersion() {
        return numeroVersion;
    }

    public VersionHorario numeroVersion(Integer numeroVersion) {
        this.numeroVersion = numeroVersion;
        return this;
    }

    public void setNumeroVersion(Integer numeroVersion) {
        this.numeroVersion = numeroVersion;
    }

    public Estado getEstado() {
        return estado;
    }

    public VersionHorario estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getTrimestreVigenteId() {
        return trimestreVigenteId;
    }

    public VersionHorario trimestreVigenteId(Integer trimestreVigenteId) {
        this.trimestreVigenteId = trimestreVigenteId;
        return this;
    }

    public void setTrimestreVigenteId(Integer trimestreVigenteId) {
        this.trimestreVigenteId = trimestreVigenteId;
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public VersionHorario horarios(Set<Horario> horarios) {
        this.horarios = horarios;
        return this;
    }

    public VersionHorario addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setVersionHorario(this);
        return this;
    }

    public VersionHorario removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setVersionHorario(null);
        return this;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public TrimestreVigente getVersionTrimestre() {
        return versionTrimestre;
    }

    public VersionHorario versionTrimestre(TrimestreVigente trimestreVigente) {
        this.versionTrimestre = trimestreVigente;
        return this;
    }

    public void setVersionTrimestre(TrimestreVigente trimestreVigente) {
        this.versionTrimestre = trimestreVigente;
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
        VersionHorario versionHorario = (VersionHorario) o;
        if (versionHorario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), versionHorario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VersionHorario{" +
            "id=" + getId() +
            ", numeroVersion=" + getNumeroVersion() +
            ", estado='" + getEstado() + "'" +
            ", trimestreVigenteId=" + getTrimestreVigenteId() +
            "}";
    }
}
