package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Jornada.
 */
@Entity
@Table(name = "jornada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Jornada implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "sigla_jornada", length = 10, nullable = false, unique = true)
    private String siglaJornada;

    @NotNull
    @Size(max = 85)
    @Column(name = "nombre_jornada", length = 85, nullable = false, unique = true)
    private String nombreJornada;

    @NotNull
    @Size(max = 245)
    @Column(name = "descripcion", length = 245, nullable = false)
    private String descripcion;

    @NotNull
    @Size(max = 400)
    @Column(name = "imagen_url", length = 400, nullable = false)
    private String imagenUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "jornada")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trimestre> trimestre4S = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiglaJornada() {
        return siglaJornada;
    }

    public Jornada siglaJornada(String siglaJornada) {
        this.siglaJornada = siglaJornada;
        return this;
    }

    public void setSiglaJornada(String siglaJornada) {
        this.siglaJornada = siglaJornada;
    }

    public String getNombreJornada() {
        return nombreJornada;
    }

    public Jornada nombreJornada(String nombreJornada) {
        this.nombreJornada = nombreJornada;
        return this;
    }

    public void setNombreJornada(String nombreJornada) {
        this.nombreJornada = nombreJornada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Jornada descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public Jornada imagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Estado getEstado() {
        return estado;
    }

    public Jornada estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<Trimestre> getTrimestre4S() {
        return trimestre4S;
    }

    public Jornada trimestre4S(Set<Trimestre> trimestres) {
        this.trimestre4S = trimestres;
        return this;
    }

    public Jornada addTrimestre4(Trimestre trimestre) {
        this.trimestre4S.add(trimestre);
        trimestre.setJornada(this);
        return this;
    }

    public Jornada removeTrimestre4(Trimestre trimestre) {
        this.trimestre4S.remove(trimestre);
        trimestre.setJornada(null);
        return this;
    }

    public void setTrimestre4S(Set<Trimestre> trimestres) {
        this.trimestre4S = trimestres;
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
        Jornada jornada = (Jornada) o;
        if (jornada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jornada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Jornada{" +
            "id=" + getId() +
            ", siglaJornada='" + getSiglaJornada() + "'" +
            ", nombreJornada='" + getNombreJornada() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
