package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.Vinculacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vinculacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VinculacionRepository extends JpaRepository<Vinculacion, Long> {

}
