package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DiaJornada.
 */
@Entity
@Table(name = "dia_jornada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DiaJornada implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private Integer horaInicio;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private Integer horaFin;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("diaJornadas")
    private JornadaInstructor jornadaInstructor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoraInicio() {
        return horaInicio;
    }

    public DiaJornada horaInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getHoraFin() {
        return horaFin;
    }

    public DiaJornada horaFin(Integer horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public void setHoraFin(Integer horaFin) {
        this.horaFin = horaFin;
    }

    public JornadaInstructor getJornadaInstructor() {
        return jornadaInstructor;
    }

    public DiaJornada jornadaInstructor(JornadaInstructor jornadaInstructor) {
        this.jornadaInstructor = jornadaInstructor;
        return this;
    }

    public void setJornadaInstructor(JornadaInstructor jornadaInstructor) {
        this.jornadaInstructor = jornadaInstructor;
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
        DiaJornada diaJornada = (DiaJornada) o;
        if (diaJornada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diaJornada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiaJornada{" +
            "id=" + getId() +
            ", horaInicio=" + getHoraInicio() +
            ", horaFin=" + getHoraFin() +
            "}";
    }
}
