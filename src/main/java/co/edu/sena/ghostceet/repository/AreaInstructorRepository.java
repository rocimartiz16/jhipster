package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.AreaInstructor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AreaInstructor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaInstructorRepository extends JpaRepository<AreaInstructor, Long> {

}
