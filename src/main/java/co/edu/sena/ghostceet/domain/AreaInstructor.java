package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import co.edu.sena.ghostceet.domain.enumeration.Estado;

/**
 * A AreaInstructor.
 */
@Entity
@Table(name = "area_instructor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AreaInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("areaInstructors")
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("areaInstructors")
    private Instructor instructor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public AreaInstructor estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Area getArea() {
        return area;
    }

    public AreaInstructor area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public AreaInstructor instructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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
        AreaInstructor areaInstructor = (AreaInstructor) o;
        if (areaInstructor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), areaInstructor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AreaInstructor{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
