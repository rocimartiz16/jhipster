package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.ResultadosVistos;
import co.edu.sena.ghostceet.repository.ResultadosVistosRepository;
import co.edu.sena.ghostceet.web.rest.errors.BadRequestAlertException;
import co.edu.sena.ghostceet.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ResultadosVistos.
 */
@RestController
@RequestMapping("/api")
public class ResultadosVistosResource {

    private final Logger log = LoggerFactory.getLogger(ResultadosVistosResource.class);

    private static final String ENTITY_NAME = "resultadosVistos";

    private final ResultadosVistosRepository resultadosVistosRepository;

    public ResultadosVistosResource(ResultadosVistosRepository resultadosVistosRepository) {
        this.resultadosVistosRepository = resultadosVistosRepository;
    }

    /**
     * POST  /resultados-vistos : Create a new resultadosVistos.
     *
     * @param resultadosVistos the resultadosVistos to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultadosVistos, or with status 400 (Bad Request) if the resultadosVistos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resultados-vistos")
    public ResponseEntity<ResultadosVistos> createResultadosVistos(@Valid @RequestBody ResultadosVistos resultadosVistos) throws URISyntaxException {
        log.debug("REST request to save ResultadosVistos : {}", resultadosVistos);
        if (resultadosVistos.getId() != null) {
            throw new BadRequestAlertException("A new resultadosVistos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultadosVistos result = resultadosVistosRepository.save(resultadosVistos);
        return ResponseEntity.created(new URI("/api/resultados-vistos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resultados-vistos : Updates an existing resultadosVistos.
     *
     * @param resultadosVistos the resultadosVistos to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultadosVistos,
     * or with status 400 (Bad Request) if the resultadosVistos is not valid,
     * or with status 500 (Internal Server Error) if the resultadosVistos couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resultados-vistos")
    public ResponseEntity<ResultadosVistos> updateResultadosVistos(@Valid @RequestBody ResultadosVistos resultadosVistos) throws URISyntaxException {
        log.debug("REST request to update ResultadosVistos : {}", resultadosVistos);
        if (resultadosVistos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultadosVistos result = resultadosVistosRepository.save(resultadosVistos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultadosVistos.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resultados-vistos : get all the resultadosVistos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resultadosVistos in body
     */
    @GetMapping("/resultados-vistos")
    public List<ResultadosVistos> getAllResultadosVistos() {
        log.debug("REST request to get all ResultadosVistos");
        return resultadosVistosRepository.findAll();
    }

    /**
     * GET  /resultados-vistos/:id : get the "id" resultadosVistos.
     *
     * @param id the id of the resultadosVistos to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultadosVistos, or with status 404 (Not Found)
     */
    @GetMapping("/resultados-vistos/{id}")
    public ResponseEntity<ResultadosVistos> getResultadosVistos(@PathVariable Long id) {
        log.debug("REST request to get ResultadosVistos : {}", id);
        Optional<ResultadosVistos> resultadosVistos = resultadosVistosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultadosVistos);
    }

    /**
     * DELETE  /resultados-vistos/:id : delete the "id" resultadosVistos.
     *
     * @param id the id of the resultadosVistos to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resultados-vistos/{id}")
    public ResponseEntity<Void> deleteResultadosVistos(@PathVariable Long id) {
        log.debug("REST request to delete ResultadosVistos : {}", id);
        resultadosVistosRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
