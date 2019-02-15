package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.PlaneacionTrimestre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlaneacionTrimestre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaneacionTrimestreRepository extends JpaRepository<PlaneacionTrimestre, Long> {

}
