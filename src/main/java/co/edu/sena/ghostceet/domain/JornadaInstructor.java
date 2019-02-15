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
 * A JornadaInstructor.
 */
@Entity
@Table(name = "jornada_instructor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JornadaInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "nombre_jornada", length = 80, nullable = false)
    private String nombreJornada;

    @NotNull
    @Size(max = 200)
    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "jornadaInstructor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DiaJornada> diaJornadas = new HashSet<>();
    @OneToMany(mappedBy = "jornada")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DisponibilidadHoraria> disponibilidadHorarias = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreJornada() {
        return nombreJornada;
    }

    public JornadaInstructor nombreJornada(String nombreJornada) {
        this.nombreJornada = nombreJornada;
        return this;
    }

    public void setNombreJornada(String nombreJornada) {
        this.nombreJornada = nombreJornada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public JornadaInstructor descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public JornadaInstructor estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<DiaJornada> getDiaJornadas() {
        return diaJornadas;
    }

    public JornadaInstructor diaJornadas(Set<DiaJornada> diaJornadas) {
        this.diaJornadas = diaJornadas;
        return this;
    }

    public JornadaInstructor addDiaJornada(DiaJornada diaJornada) {
        this.diaJornadas.add(diaJornada);
        diaJornada.setJornadaInstructor(this);
        return this;
    }

    public JornadaInstructor removeDiaJornada(DiaJornada diaJornada) {
        this.diaJornadas.remove(diaJornada);
        diaJornada.setJornadaInstructor(null);
        return this;
    }

    public void setDiaJornadas(Set<DiaJornada> diaJornadas) {
        this.diaJornadas = diaJornadas;
    }

    public Set<DisponibilidadHoraria> getDisponibilidadHorarias() {
        return disponibilidadHorarias;
    }

    public JornadaInstructor disponibilidadHorarias(Set<DisponibilidadHoraria> disponibilidadHorarias) {
        this.disponibilidadHorarias = disponibilidadHorarias;
        return this;
    }

    public JornadaInstructor addDisponibilidadHoraria(DisponibilidadHoraria disponibilidadHoraria) {
        this.disponibilidadHorarias.add(disponibilidadHoraria);
        disponibilidadHoraria.setJornada(this);
        return this;
    }

    public JornadaInstructor removeDisponibilidadHoraria(DisponibilidadHoraria disponibilidadHoraria) {
        this.disponibilidadHorarias.remove(disponibilidadHoraria);
        disponibilidadHoraria.setJornada(null);
        return this;
    }

    public void setDisponibilidadHorarias(Set<DisponibilidadHoraria> disponibilidadHorarias) {
        this.disponibilidadHorarias = disponibilidadHorarias;
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
        JornadaInstructor jornadaInstructor = (JornadaInstructor) o;
        if (jornadaInstructor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jornadaInstructor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JornadaInstructor{" +
            "id=" + getId() +
            ", nombreJornada='" + getNombreJornada() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
