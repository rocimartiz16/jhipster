package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.NivelFormacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NivelFormacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NivelFormacionRepository extends JpaRepository<NivelFormacion, Long> {

}
