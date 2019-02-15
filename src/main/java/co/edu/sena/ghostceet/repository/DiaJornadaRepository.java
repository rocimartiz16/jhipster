package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.DiaJornada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiaJornada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaJornadaRepository extends JpaRepository<DiaJornada, Long> {

}
