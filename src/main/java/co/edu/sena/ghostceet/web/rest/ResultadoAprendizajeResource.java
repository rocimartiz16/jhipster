package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.ResultadoAprendizaje;
import co.edu.sena.ghostceet.repository.ResultadoAprendizajeRepository;
import co.edu.sena.ghostceet.web.rest.errors.BadRequestAlertException;
import co.edu.sena.ghostceet.web.rest.util.HeaderUtil;
import co.edu.sena.ghostceet.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ResultadoAprendizaje.
 */
@RestController
@RequestMapping("/api")
public class ResultadoAprendizajeResource {

    private final Logger log = LoggerFactory.getLogger(ResultadoAprendizajeResource.class);

    private static final String ENTITY_NAME = "resultadoAprendizaje";

    private final ResultadoAprendizajeRepository resultadoAprendizajeRepository;

    public ResultadoAprendizajeResource(ResultadoAprendizajeRepository resultadoAprendizajeRepository) {
        this.resultadoAprendizajeRepository = resultadoAprendizajeRepository;
    }

    /**
     * POST  /resultado-aprendizajes : Create a new resultadoAprendizaje.
     *
     * @param resultadoAprendizaje the resultadoAprendizaje to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultadoAprendizaje, or with status 400 (Bad Request) if the resultadoAprendizaje has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resultado-aprendizajes")
    public ResponseEntity<ResultadoAprendizaje> createResultadoAprendizaje(@Valid @RequestBody ResultadoAprendizaje resultadoAprendizaje) throws URISyntaxException {
        log.debug("REST request to save ResultadoAprendizaje : {}", resultadoAprendizaje);
        if (resultadoAprendizaje.getId() != null) {
            throw new BadRequestAlertException("A new resultadoAprendizaje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultadoAprendizaje result = resultadoAprendizajeRepository.save(resultadoAprendizaje);
        return ResponseEntity.created(new URI("/api/resultado-aprendizajes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resultado-aprendizajes : Updates an existing resultadoAprendizaje.
     *
     * @param resultadoAprendizaje the resultadoAprendizaje to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultadoAprendizaje,
     * or with status 400 (Bad Request) if the resultadoAprendizaje is not valid,
     * or with status 500 (Internal Server Error) if the resultadoAprendizaje couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resultado-aprendizajes")
    public ResponseEntity<ResultadoAprendizaje> updateResultadoAprendizaje(@Valid @RequestBody ResultadoAprendizaje resultadoAprendizaje) throws URISyntaxException {
        log.debug("REST request to update ResultadoAprendizaje : {}", resultadoAprendizaje);
        if (resultadoAprendizaje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultadoAprendizaje result = resultadoAprendizajeRepository.save(resultadoAprendizaje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultadoAprendizaje.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resultado-aprendizajes : get all the resultadoAprendizajes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resultadoAprendizajes in body
     */
    @GetMapping("/resultado-aprendizajes")
    public ResponseEntity<List<ResultadoAprendizaje>> getAllResultadoAprendizajes(Pageable pageable) {
        log.debug("REST request to get a page of ResultadoAprendizajes");
        Page<ResultadoAprendizaje> page = resultadoAprendizajeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resultado-aprendizajes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /resultado-aprendizajes/:id : get the "id" resultadoAprendizaje.
     *
     * @param id the id of the resultadoAprendizaje to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultadoAprendizaje, or with status 404 (Not Found)
     */
    @GetMapping("/resultado-aprendizajes/{id}")
    public ResponseEntity<ResultadoAprendizaje> getResultadoAprendizaje(@PathVariable Long id) {
        log.debug("REST request to get ResultadoAprendizaje : {}", id);
        Optional<ResultadoAprendizaje> resultadoAprendizaje = resultadoAprendizajeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultadoAprendizaje);
    }

    /**
     * DELETE  /resultado-aprendizajes/:id : delete the "id" resultadoAprendizaje.
     *
     * @param id the id of the resultadoAprendizaje to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resultado-aprendizajes/{id}")
    public ResponseEntity<Void> deleteResultadoAprendizaje(@PathVariable Long id) {
        log.debug("REST request to delete ResultadoAprendizaje : {}", id);
        resultadoAprendizajeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
