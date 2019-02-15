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
 * A NivelFormacion.
 */
@Entity
@Table(name = "nivel_formacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NivelFormacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nivel_formacion", length = 20, nullable = false, unique = true)
    private String nivelFormacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "nivelFormacion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Programa> programa1S = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNivelFormacion() {
        return nivelFormacion;
    }

    public NivelFormacion nivelFormacion(String nivelFormacion) {
        this.nivelFormacion = nivelFormacion;
        return this;
    }

    public void setNivelFormacion(String nivelFormacion) {
        this.nivelFormacion = nivelFormacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public NivelFormacion estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<Programa> getPrograma1S() {
        return programa1S;
    }

    public NivelFormacion programa1S(Set<Programa> programas) {
        this.programa1S = programas;
        return this;
    }

    public NivelFormacion addPrograma1(Programa programa) {
        this.programa1S.add(programa);
        programa.setNivelFormacion(this);
        return this;
    }

    public NivelFormacion removePrograma1(Programa programa) {
        this.programa1S.remove(programa);
        programa.setNivelFormacion(null);
        return this;
    }

    public void setPrograma1S(Set<Programa> programas) {
        this.programa1S = programas;
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
        NivelFormacion nivelFormacion = (NivelFormacion) o;
        if (nivelFormacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nivelFormacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NivelFormacion{" +
            "id=" + getId() +
            ", nivelFormacion='" + getNivelFormacion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
