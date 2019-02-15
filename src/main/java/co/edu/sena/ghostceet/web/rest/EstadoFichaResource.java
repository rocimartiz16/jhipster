package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.EstadoFicha;
import co.edu.sena.ghostceet.repository.EstadoFichaRepository;
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
 * REST controller for managing EstadoFicha.
 */
@RestController
@RequestMapping("/api")
public class EstadoFichaResource {

    private final Logger log = LoggerFactory.getLogger(EstadoFichaResource.class);

    private static final String ENTITY_NAME = "estadoFicha";

    private final EstadoFichaRepository estadoFichaRepository;

    public EstadoFichaResource(EstadoFichaRepository estadoFichaRepository) {
        this.estadoFichaRepository = estadoFichaRepository;
    }

    /**
     * POST  /estado-fichas : Create a new estadoFicha.
     *
     * @param estadoFicha the estadoFicha to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estadoFicha, or with status 400 (Bad Request) if the estadoFicha has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estado-fichas")
    public ResponseEntity<EstadoFicha> createEstadoFicha(@Valid @RequestBody EstadoFicha estadoFicha) throws URISyntaxException {
        log.debug("REST request to save EstadoFicha : {}", estadoFicha);
        if (estadoFicha.getId() != null) {
            throw new BadRequestAlertException("A new estadoFicha cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoFicha result = estadoFichaRepository.save(estadoFicha);
        return ResponseEntity.created(new URI("/api/estado-fichas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estado-fichas : Updates an existing estadoFicha.
     *
     * @param estadoFicha the estadoFicha to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estadoFicha,
     * or with status 400 (Bad Request) if the estadoFicha is not valid,
     * or with status 500 (Internal Server Error) if the estadoFicha couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estado-fichas")
    public ResponseEntity<EstadoFicha> updateEstadoFicha(@Valid @RequestBody EstadoFicha estadoFicha) throws URISyntaxException {
        log.debug("REST request to update EstadoFicha : {}", estadoFicha);
        if (estadoFicha.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoFicha result = estadoFichaRepository.save(estadoFicha);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estadoFicha.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estado-fichas : get all the estadoFichas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estadoFichas in body
     */
    @GetMapping("/estado-fichas")
    public ResponseEntity<List<EstadoFicha>> getAllEstadoFichas(Pageable pageable) {
        log.debug("REST request to get a page of EstadoFichas");
        Page<EstadoFicha> page = estadoFichaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estado-fichas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /estado-fichas/:id : get the "id" estadoFicha.
     *
     * @param id the id of the estadoFicha to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estadoFicha, or with status 404 (Not Found)
     */
    @GetMapping("/estado-fichas/{id}")
    public ResponseEntity<EstadoFicha> getEstadoFicha(@PathVariable Long id) {
        log.debug("REST request to get EstadoFicha : {}", id);
        Optional<EstadoFicha> estadoFicha = estadoFichaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoFicha);
    }

    /**
     * DELETE  /estado-fichas/:id : delete the "id" estadoFicha.
     *
     * @param id the id of the estadoFicha to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estado-fichas/{id}")
    public ResponseEntity<Void> deleteEstadoFicha(@PathVariable Long id) {
        log.debug("REST request to delete EstadoFicha : {}", id);
        estadoFichaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
