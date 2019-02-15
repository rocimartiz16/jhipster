package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.PlaneacionActividad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlaneacionActividad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaneacionActividadRepository extends JpaRepository<PlaneacionActividad, Long> {

}
