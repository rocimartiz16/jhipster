package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.DisponibilidadHoraria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DisponibilidadHoraria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisponibilidadHorariaRepository extends JpaRepository<DisponibilidadHoraria, Long> {

}
