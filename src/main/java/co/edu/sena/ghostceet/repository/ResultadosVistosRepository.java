package co.edu.sena.ghostceet.repository;

import co.edu.sena.ghostceet.domain.ResultadosVistos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResultadosVistos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadosVistosRepository extends JpaRepository<ResultadosVistos, Long> {

}
