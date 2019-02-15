package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.Ambiente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ambiente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Long> {

}
