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

/**
 * A FichaTrimestre.
 */
@Entity
@Table(name = "ficha_trimestre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FichaTrimestre implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "fichaTrimestre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultadosVistos> resultadosVistos = new HashSet<>();
    @OneToMany(mappedBy = "fichaTrimestre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Horario> horarios = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("fichaTrimestres")
    private Ficha ficha;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ResultadosVistos> getResultadosVistos() {
        return resultadosVistos;
    }

    public FichaTrimestre resultadosVistos(Set<ResultadosVistos> resultadosVistos) {
        this.resultadosVistos = resultadosVistos;
        return this;
    }

    public FichaTrimestre addResultadosVistos(ResultadosVistos resultadosVistos) {
        this.resultadosVistos.add(resultadosVistos);
        resultadosVistos.setFichaTrimestre(this);
        return this;
    }

    public FichaTrimestre removeResultadosVistos(ResultadosVistos resultadosVistos) {
        this.resultadosVistos.remove(resultadosVistos);
        resultadosVistos.setFichaTrimestre(null);
        return this;
    }

    public void setResultadosVistos(Set<ResultadosVistos> resultadosVistos) {
        this.resultadosVistos = resultadosVistos;
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public FichaTrimestre horarios(Set<Horario> horarios) {
        this.horarios = horarios;
        return this;
    }

    public FichaTrimestre addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setFichaTrimestre(this);
        return this;
    }

    public FichaTrimestre removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setFichaTrimestre(null);
        return this;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public FichaTrimestre ficha(Ficha ficha) {
        this.ficha = ficha;
        return this;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
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
        FichaTrimestre fichaTrimestre = (FichaTrimestre) o;
        if (fichaTrimestre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichaTrimestre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichaTrimestre{" +
            "id=" + getId() +
            "}";
    }
}
