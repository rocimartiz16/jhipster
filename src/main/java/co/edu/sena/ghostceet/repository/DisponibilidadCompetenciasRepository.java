package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.DisponibilidadCompetencias;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DisponibilidadCompetencias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisponibilidadCompetenciasRepository extends JpaRepository<DisponibilidadCompetencias, Long> {

}
