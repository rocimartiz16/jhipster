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
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "codigo", length = 100, nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @NotNull
    @Size(max = 300)
    @Column(name = "nombre_proyecto", length = 300, nullable = false)
    private String nombreProyecto;

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FaseProyecto> faseProyecto6S = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("proyecto2S")
    private Programa programa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Proyecto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Estado getEstado() {
        return estado;
    }

    public Proyecto estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public Proyecto nombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
        return this;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public Set<FaseProyecto> getFaseProyecto6S() {
        return faseProyecto6S;
    }

    public Proyecto faseProyecto6S(Set<FaseProyecto> faseProyectos) {
        this.faseProyecto6S = faseProyectos;
        return this;
    }

    public Proyecto addFaseProyecto6(FaseProyecto faseProyecto) {
        this.faseProyecto6S.add(faseProyecto);
        faseProyecto.setProyecto(this);
        return this;
    }

    public Proyecto removeFaseProyecto6(FaseProyecto faseProyecto) {
        this.faseProyecto6S.remove(faseProyecto);
        faseProyecto.setProyecto(null);
        return this;
    }

    public void setFaseProyecto6S(Set<FaseProyecto> faseProyectos) {
        this.faseProyecto6S = faseProyectos;
    }

    public Programa getPrograma() {
        return programa;
    }

    public Proyecto programa(Programa programa) {
        this.programa = programa;
        return this;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
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
        Proyecto proyecto = (Proyecto) o;
        if (proyecto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proyecto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", nombreProyecto='" + getNombreProyecto() + "'" +
            "}";
    }
}
