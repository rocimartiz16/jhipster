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
 * A Area.
 */
@Entity
@Table(name = "area")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "nombre_area", length = 40, nullable = false, unique = true)
    private String nombreArea;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Size(max = 400)
    @Column(name = "url_logo", length = 400)
    private String urlLogo;

    @OneToMany(mappedBy = "area")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AreaInstructor> areaInstructors = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public Area nombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
        return this;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public Estado getEstado() {
        return estado;
    }

    public Area estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public Area urlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
        return this;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Set<AreaInstructor> getAreaInstructors() {
        return areaInstructors;
    }

    public Area areaInstructors(Set<AreaInstructor> areaInstructors) {
        this.areaInstructors = areaInstructors;
        return this;
    }

    public Area addAreaInstructor(AreaInstructor areaInstructor) {
        this.areaInstructors.add(areaInstructor);
        areaInstructor.setArea(this);
        return this;
    }

    public Area removeAreaInstructor(AreaInstructor areaInstructor) {
        this.areaInstructors.remove(areaInstructor);
        areaInstructor.setArea(null);
        return this;
    }

    public void setAreaInstructors(Set<AreaInstructor> areaInstructors) {
        this.areaInstructors = areaInstructors;
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
        Area area = (Area) o;
        if (area.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), area.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Area{" +
            "id=" + getId() +
            ", nombreArea='" + getNombreArea() + "'" +
            ", estado='" + getEstado() + "'" +
            ", urlLogo='" + getUrlLogo() + "'" +
            "}";
    }
}
