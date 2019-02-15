package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ResultadosVistos.
 */
@Entity
@Table(name = "resultados_vistos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultadosVistos implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("resultadosVistos")
    private ResultadoAprendizaje resultadoAprendizaje;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("resultadosVistos")
    private FichaTrimestre fichaTrimestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResultadoAprendizaje getResultadoAprendizaje() {
        return resultadoAprendizaje;
    }

    public ResultadosVistos resultadoAprendizaje(ResultadoAprendizaje resultadoAprendizaje) {
        this.resultadoAprendizaje = resultadoAprendizaje;
        return this;
    }

    public void setResultadoAprendizaje(ResultadoAprendizaje resultadoAprendizaje) {
        this.resultadoAprendizaje = resultadoAprendizaje;
    }

    public FichaTrimestre getFichaTrimestre() {
        return fichaTrimestre;
    }

    public ResultadosVistos fichaTrimestre(FichaTrimestre fichaTrimestre) {
        this.fichaTrimestre = fichaTrimestre;
        return this;
    }

    public void setFichaTrimestre(FichaTrimestre fichaTrimestre) {
        this.fichaTrimestre = fichaTrimestre;
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
        ResultadosVistos resultadosVistos = (ResultadosVistos) o;
        if (resultadosVistos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultadosVistos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultadosVistos{" +
            "id=" + getId() +
            "}";
    }
}
