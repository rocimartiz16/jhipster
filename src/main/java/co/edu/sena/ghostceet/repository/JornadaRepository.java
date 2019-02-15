package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.Jornada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Jornada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {

}
