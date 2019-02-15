package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.TipoAmbiente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoAmbiente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoAmbienteRepository extends JpaRepository<TipoAmbiente, Long> {

}
