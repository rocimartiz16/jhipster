package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.Anio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Anio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnioRepository extends JpaRepository<Anio, Long> {

}
