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

/**
 * A ResultadoAprendizaje.
 */
@Entity
@Table(name = "resultado_aprendizaje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultadoAprendizaje implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "codigo", length = 100, nullable = false)
    private String codigo;

    @NotNull
    @Size(max = 400)
    @Column(name = "descripcion", length = 400, nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "resultadoAprendizaje")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultadosVistos> resultadosVistos = new HashSet<>();
    @OneToMany(mappedBy = "resultadoAprendizaje")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LimitacionAmbiente> limitacionAmbientes = new HashSet<>();
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

    public ResultadoAprendizaje codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ResultadoAprendizaje descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<ResultadosVistos> getResultadosVistos() {
        return resultadosVistos;
    }

    public ResultadoAprendizaje resultadosVistos(Set<ResultadosVistos> resultadosVistos) {
        this.resultadosVistos = resultadosVistos;
        return this;
    }

    public ResultadoAprendizaje addResultadosVistos(ResultadosVistos resultadosVistos) {
        this.resultadosVistos.add(resultadosVistos);
        resultadosVistos.setResultadoAprendizaje(this);
        return this;
    }

    public ResultadoAprendizaje removeResultadosVistos(ResultadosVistos resultadosVistos) {
        this.resultadosVistos.remove(resultadosVistos);
        resultadosVistos.setResultadoAprendizaje(null);
        return this;
    }

    public void setResultadosVistos(Set<ResultadosVistos> resultadosVistos) {
        this.resultadosVistos = resultadosVistos;
    }

    public Set<LimitacionAmbiente> getLimitacionAmbientes() {
        return limitacionAmbientes;
    }

    public ResultadoAprendizaje limitacionAmbientes(Set<LimitacionAmbiente> limitacionAmbientes) {
        this.limitacionAmbientes = limitacionAmbientes;
        return this;
    }

    public ResultadoAprendizaje addLimitacionAmbiente(LimitacionAmbiente limitacionAmbiente) {
        this.limitacionAmbientes.add(limitacionAmbiente);
        limitacionAmbiente.setResultadoAprendizaje(this);
        return this;
    }

    public ResultadoAprendizaje removeLimitacionAmbiente(LimitacionAmbiente limitacionAmbiente) {
        this.limitacionAmbientes.remove(limitacionAmbiente);
        limitacionAmbiente.setResultadoAprendizaje(null);
        return this;
    }

    public void setLimitacionAmbientes(Set<LimitacionAmbiente> limitacionAmbientes) {
        this.limitacionAmbientes = limitacionAmbientes;
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
        ResultadoAprendizaje resultadoAprendizaje = (ResultadoAprendizaje) o;
        if (resultadoAprendizaje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultadoAprendizaje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultadoAprendizaje{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
