package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.FaseProyecto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FaseProyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaseProyectoRepository extends JpaRepository<FaseProyecto, Long> {

}
