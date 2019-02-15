package co.edu.sena.ghostceet.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import co.edu.sena.ghostceet.domain.enumeration.Estado;

/**
 * A Ficha.
 */
@Entity
@Table(name = "ficha")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ficha implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "numero", length = 100, nullable = false, unique = true)
    private String numero;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private ZonedDateTime fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private ZonedDateTime fechaFin;

    @NotNull
    @Column(name = "ruta", nullable = false)
    private String ruta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "ficha")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FichaTrimestre> fichaTrimestres = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("fichas")
    private Programa programa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("fichas")
    private EstadoFicha estadoFicha;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Ficha numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getFechaInicio() {
        return fechaInicio;
    }

    public Ficha fechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public Ficha fechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getRuta() {
        return ruta;
    }

    public Ficha ruta(String ruta) {
        this.ruta = ruta;
        return this;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Estado getEstado() {
        return estado;
    }

    public Ficha estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<FichaTrimestre> getFichaTrimestres() {
        return fichaTrimestres;
    }

    public Ficha fichaTrimestres(Set<FichaTrimestre> fichaTrimestres) {
        this.fichaTrimestres = fichaTrimestres;
        return this;
    }

    public Ficha addFichaTrimestre(FichaTrimestre fichaTrimestre) {
        this.fichaTrimestres.add(fichaTrimestre);
        fichaTrimestre.setFicha(this);
        return this;
    }

    public Ficha removeFichaTrimestre(FichaTrimestre fichaTrimestre) {
        this.fichaTrimestres.remove(fichaTrimestre);
        fichaTrimestre.setFicha(null);
        return this;
    }

    public void setFichaTrimestres(Set<FichaTrimestre> fichaTrimestres) {
        this.fichaTrimestres = fichaTrimestres;
    }

    public Programa getPrograma() {
        return programa;
    }

    public Ficha programa(Programa programa) {
        this.programa = programa;
        return this;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public EstadoFicha getEstadoFicha() {
        return estadoFicha;
    }

    public Ficha estadoFicha(EstadoFicha estadoFicha) {
        this.estadoFicha = estadoFicha;
        return this;
    }

    public void setEstadoFicha(EstadoFicha estadoFicha) {
        this.estadoFicha = estadoFicha;
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
        Ficha ficha = (Ficha) o;
        if (ficha.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ficha.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ficha{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", ruta='" + getRuta() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
