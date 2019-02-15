package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.JornadaInstructor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JornadaInstructor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JornadaInstructorRepository extends JpaRepository<JornadaInstructor, Long> {

}
