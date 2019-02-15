package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.ActividadProyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActividadProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActividadProyectoRepository extends JpaRepository<ActividadProyecto, Long> {

}
