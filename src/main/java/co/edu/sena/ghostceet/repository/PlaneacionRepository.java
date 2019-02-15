package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.Planeacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Planeacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaneacionRepository extends JpaRepository<Planeacion, Long> {

}
