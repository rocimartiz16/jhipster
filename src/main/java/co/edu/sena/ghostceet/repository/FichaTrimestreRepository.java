package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.FichaTrimestre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FichaTrimestre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichaTrimestreRepository extends JpaRepository<FichaTrimestre, Long> {

}
