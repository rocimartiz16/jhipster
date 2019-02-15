package co.edu.sena.ghostceet.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import co.edu.sena.ghostceet.domain.enumeration.Estado;

/**
 * A Planeacion.
 */
@Entity
@Table(name = "planeacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Planeacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre_planeacion", length = 150, nullable = false, unique = true)
    private String nombrePlaneacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePlaneacion() {
        return nombrePlaneacion;
    }

    public Planeacion nombrePlaneacion(String nombrePlaneacion) {
        this.nombrePlaneacion = nombrePlaneacion;
        return this;
    }

    public void setNombrePlaneacion(String nombrePlaneacion) {
        this.nombrePlaneacion = nombrePlaneacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public Planeacion estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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
        Planeacion planeacion = (Planeacion) o;
        if (planeacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planeacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Planeacion{" +
            "id=" + getId() +
            ", nombrePlaneacion='" + getNombrePlaneacion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
