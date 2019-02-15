package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.FaseProyecto;
import co.edu.sena.ghostceet.repository.FaseProyectoRepository;
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
 * REST controller for managing FaseProyecto.
 */
@RestController
@RequestMapping("/api")
public class FaseProyectoResource {

    private final Logger log = LoggerFactory.getLogger(FaseProyectoResource.class);

    private static final String ENTITY_NAME = "faseProyecto";

    private final FaseProyectoRepository faseProyectoRepository;

    public FaseProyectoResource(FaseProyectoRepository faseProyectoRepository) {
        this.faseProyectoRepository = faseProyectoRepository;
    }

    /**
     * POST  /fase-proyectos : Create a new faseProyecto.
     *
     * @param faseProyecto the faseProyecto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faseProyecto, or with status 400 (Bad Request) if the faseProyecto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fase-proyectos")
    public ResponseEntity<FaseProyecto> createFaseProyecto(@Valid @RequestBody FaseProyecto faseProyecto) throws URISyntaxException {
        log.debug("REST request to save FaseProyecto : {}", faseProyecto);
        if (faseProyecto.getId() != null) {
            throw new BadRequestAlertException("A new faseProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaseProyecto result = faseProyectoRepository.save(faseProyecto);
        return ResponseEntity.created(new URI("/api/fase-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fase-proyectos : Updates an existing faseProyecto.
     *
     * @param faseProyecto the faseProyecto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faseProyecto,
     * or with status 400 (Bad Request) if the faseProyecto is not valid,
     * or with status 500 (Internal Server Error) if the faseProyecto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fase-proyectos")
    public ResponseEntity<FaseProyecto> updateFaseProyecto(@Valid @RequestBody FaseProyecto faseProyecto) throws URISyntaxException {
        log.debug("REST request to update FaseProyecto : {}", faseProyecto);
        if (faseProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FaseProyecto result = faseProyectoRepository.save(faseProyecto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, faseProyecto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fase-proyectos : get all the faseProyectos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of faseProyectos in body
     */
    @GetMapping("/fase-proyectos")
    public ResponseEntity<List<FaseProyecto>> getAllFaseProyectos(Pageable pageable) {
        log.debug("REST request to get a page of FaseProyectos");
        Page<FaseProyecto> page = faseProyectoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fase-proyectos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /fase-proyectos/:id : get the "id" faseProyecto.
     *
     * @param id the id of the faseProyecto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faseProyecto, or with status 404 (Not Found)
     */
    @GetMapping("/fase-proyectos/{id}")
    public ResponseEntity<FaseProyecto> getFaseProyecto(@PathVariable Long id) {
        log.debug("REST request to get FaseProyecto : {}", id);
        Optional<FaseProyecto> faseProyecto = faseProyectoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(faseProyecto);
    }

    /**
     * DELETE  /fase-proyectos/:id : delete the "id" faseProyecto.
     *
     * @param id the id of the faseProyecto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fase-proyectos/{id}")
    public ResponseEntity<Void> deleteFaseProyecto(@PathVariable Long id) {
        log.debug("REST request to delete FaseProyecto : {}", id);
        faseProyectoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
