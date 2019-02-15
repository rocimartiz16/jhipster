package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.VersionHorario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VersionHorario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VersionHorarioRepository extends JpaRepository<VersionHorario, Long> {

}
