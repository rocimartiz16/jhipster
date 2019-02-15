package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LimitacionAmbiente.
 */
@Entity
@Table(name = "limitacion_ambiente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LimitacionAmbiente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("resultadoAprendizajes")
    private ResultadoAprendizaje resultadoAprendizaje;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ambientes")
    private Ambiente ambiente;

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

    public LimitacionAmbiente resultadoAprendizaje(ResultadoAprendizaje resultadoAprendizaje) {
        this.resultadoAprendizaje = resultadoAprendizaje;
        return this;
    }

    public void setResultadoAprendizaje(ResultadoAprendizaje resultadoAprendizaje) {
        this.resultadoAprendizaje = resultadoAprendizaje;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public LimitacionAmbiente ambiente(Ambiente ambiente) {
        this.ambiente = ambiente;
        return this;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
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
        LimitacionAmbiente limitacionAmbiente = (LimitacionAmbiente) o;
        if (limitacionAmbiente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), limitacionAmbiente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LimitacionAmbiente{" +
            "id=" + getId() +
            "}";
    }
}
