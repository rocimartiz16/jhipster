package co.edu.sena.ghostceet.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(co.edu.sena.ghostceet.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Planeacion.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.NivelFormacion.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.NivelFormacion.class.getName() + ".programa1S", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Programa.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Programa.class.getName() + ".proyecto2S", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Programa.class.getName() + ".competencia3S", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Programa.class.getName() + ".fichas", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Proyecto.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Proyecto.class.getName() + ".faseProyecto6S", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Competencia.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.ResultadoAprendizaje.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.ResultadoAprendizaje.class.getName() + ".resultadosVistos", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.ResultadoAprendizaje.class.getName() + ".limitacionAmbientes", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Jornada.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Jornada.class.getName() + ".trimestre4S", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Trimestre.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Trimestre.class.getName() + ".planeacionTrimestre5S", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.PlaneacionTrimestre.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.PlaneacionTrimestre.class.getName() + ".planeacionActividads", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.FaseProyecto.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.FaseProyecto.class.getName() + ".actividadProyecto7S", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.ActividadProyecto.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.ActividadProyecto.class.getName() + ".planeacionActividads", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.EstadoFicha.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.EstadoFicha.class.getName() + ".fichas", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Ficha.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Ficha.class.getName() + ".fichaTrimestres", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.FichaTrimestre.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.FichaTrimestre.class.getName() + ".resultadosVistos", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.FichaTrimestre.class.getName() + ".horarios", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.ResultadosVistos.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.PlaneacionActividad.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Documento.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Documento.class.getName() + ".clientes", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Cliente.class.getName() + ".instructors", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Vinculacion.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Vinculacion.class.getName() + ".vinculacionInstructors", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Anio.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Anio.class.getName() + ".vinculacionInstructors", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Anio.class.getName() + ".trimestreVigentes", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Instructor.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Instructor.class.getName() + ".vinculacionInstructors", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Instructor.class.getName() + ".areaInstructors", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Instructor.class.getName() + ".disponibilidadCompetencias", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Instructor.class.getName() + ".disponibilidadHorarias", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Instructor.class.getName() + ".horarios", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.VinculacionInstructor.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.VinculacionInstructor.class.getName() + ".disponibilidadCompetencias", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.VinculacionInstructor.class.getName() + ".disponibilidadHorarias", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.JornadaInstructor.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.JornadaInstructor.class.getName() + ".diaJornadas", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.JornadaInstructor.class.getName() + ".disponibilidadHorarias", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.DiaJornada.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Area.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Area.class.getName() + ".areaInstructors", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.AreaInstructor.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.DisponibilidadCompetencias.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.DisponibilidadHoraria.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.TipoAmbiente.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.TipoAmbiente.class.getName() + ".ambientes", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Sede.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Sede.class.getName() + ".ambientes", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Ambiente.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Ambiente.class.getName() + ".limitacionAmbientes", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Ambiente.class.getName() + ".horarios", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.LimitacionAmbiente.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Modalidad.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Modalidad.class.getName() + ".horarios", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Dia.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Dia.class.getName() + ".horarios", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.TrimestreVigente.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.TrimestreVigente.class.getName() + ".versionHorarios", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.VersionHorario.class.getName(), jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.VersionHorario.class.getName() + ".horarios", jcacheConfiguration);
            cm.createCache(co.edu.sena.ghostceet.domain.Horario.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
