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
 * A Anio.
 */
@Entity
@Table(name = "anio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "numero_anio", unique = true)
    private Integer numeroAnio;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "anio1")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VinculacionInstructor> vinculacionInstructors = new HashSet<>();
    @OneToMany(mappedBy = "anio1")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TrimestreVigente> trimestreVigentes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroAnio() {
        return numeroAnio;
    }

    public Anio numeroAnio(Integer numeroAnio) {
        this.numeroAnio = numeroAnio;
        return this;
    }

    public void setNumeroAnio(Integer numeroAnio) {
        this.numeroAnio = numeroAnio;
    }

    public Estado getEstado() {
        return estado;
    }

    public Anio estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<VinculacionInstructor> getVinculacionInstructors() {
        return vinculacionInstructors;
    }

    public Anio vinculacionInstructors(Set<VinculacionInstructor> vinculacionInstructors) {
        this.vinculacionInstructors = vinculacionInstructors;
        return this;
    }

    public Anio addVinculacionInstructor(VinculacionInstructor vinculacionInstructor) {
        this.vinculacionInstructors.add(vinculacionInstructor);
        vinculacionInstructor.setAnio1(this);
        return this;
    }

    public Anio removeVinculacionInstructor(VinculacionInstructor vinculacionInstructor) {
        this.vinculacionInstructors.remove(vinculacionInstructor);
        vinculacionInstructor.setAnio1(null);
        return this;
    }

    public void setVinculacionInstructors(Set<VinculacionInstructor> vinculacionInstructors) {
        this.vinculacionInstructors = vinculacionInstructors;
    }

    public Set<TrimestreVigente> getTrimestreVigentes() {
        return trimestreVigentes;
    }

    public Anio trimestreVigentes(Set<TrimestreVigente> trimestreVigentes) {
        this.trimestreVigentes = trimestreVigentes;
        return this;
    }

    public Anio addTrimestreVigente(TrimestreVigente trimestreVigente) {
        this.trimestreVigentes.add(trimestreVigente);
        trimestreVigente.setAnio1(this);
        return this;
    }

    public Anio removeTrimestreVigente(TrimestreVigente trimestreVigente) {
        this.trimestreVigentes.remove(trimestreVigente);
        trimestreVigente.setAnio1(null);
        return this;
    }

    public void setTrimestreVigentes(Set<TrimestreVigente> trimestreVigentes) {
        this.trimestreVigentes = trimestreVigentes;
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
        Anio anio = (Anio) o;
        if (anio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Anio{" +
            "id=" + getId() +
            ", numeroAnio=" + getNumeroAnio() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
