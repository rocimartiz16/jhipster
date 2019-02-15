package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.LimitacionAmbiente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LimitacionAmbiente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LimitacionAmbienteRepository extends JpaRepository<LimitacionAmbiente, Long> {

}
