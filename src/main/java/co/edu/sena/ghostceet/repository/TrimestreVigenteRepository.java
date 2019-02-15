package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.TrimestreVigente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TrimestreVigente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrimestreVigenteRepository extends JpaRepository<TrimestreVigente, Long> {

}
