package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.ActividadProyecto;
import co.edu.sena.ghostceet.repository.ActividadProyectoRepository;
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
 * REST controller for managing ActividadProyecto.
 */
@RestController
@RequestMapping("/api")
public class ActividadProyectoResource {

    private final Logger log = LoggerFactory.getLogger(ActividadProyectoResource.class);

    private static final String ENTITY_NAME = "actividadProyecto";

    private final ActividadProyectoRepository actividadProyectoRepository;

    public ActividadProyectoResource(ActividadProyectoRepository actividadProyectoRepository) {
        this.actividadProyectoRepository = actividadProyectoRepository;
    }

    /**
     * POST  /actividad-proyectos : Create a new actividadProyecto.
     *
     * @param actividadProyecto the actividadProyecto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actividadProyecto, or with status 400 (Bad Request) if the actividadProyecto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actividad-proyectos")
    public ResponseEntity<ActividadProyecto> createActividadProyecto(@Valid @RequestBody ActividadProyecto actividadProyecto) throws URISyntaxException {
        log.debug("REST request to save ActividadProyecto : {}", actividadProyecto);
        if (actividadProyecto.getId() != null) {
            throw new BadRequestAlertException("A new actividadProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActividadProyecto result = actividadProyectoRepository.save(actividadProyecto);
        return ResponseEntity.created(new URI("/api/actividad-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actividad-proyectos : Updates an existing actividadProyecto.
     *
     * @param actividadProyecto the actividadProyecto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actividadProyecto,
     * or with status 400 (Bad Request) if the actividadProyecto is not valid,
     * or with status 500 (Internal Server Error) if the actividadProyecto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actividad-proyectos")
    public ResponseEntity<ActividadProyecto> updateActividadProyecto(@Valid @RequestBody ActividadProyecto actividadProyecto) throws URISyntaxException {
        log.debug("REST request to update ActividadProyecto : {}", actividadProyecto);
        if (actividadProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActividadProyecto result = actividadProyectoRepository.save(actividadProyecto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actividadProyecto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actividad-proyectos : get all the actividadProyectos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actividadProyectos in body
     */
    @GetMapping("/actividad-proyectos")
    public ResponseEntity<List<ActividadProyecto>> getAllActividadProyectos(Pageable pageable) {
        log.debug("REST request to get a page of ActividadProyectos");
        Page<ActividadProyecto> page = actividadProyectoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actividad-proyectos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /actividad-proyectos/:id : get the "id" actividadProyecto.
     *
     * @param id the id of the actividadProyecto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actividadProyecto, or with status 404 (Not Found)
     */
    @GetMapping("/actividad-proyectos/{id}")
    public ResponseEntity<ActividadProyecto> getActividadProyecto(@PathVariable Long id) {
        log.debug("REST request to get ActividadProyecto : {}", id);
        Optional<ActividadProyecto> actividadProyecto = actividadProyectoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(actividadProyecto);
    }

    /**
     * DELETE  /actividad-proyectos/:id : delete the "id" actividadProyecto.
     *
     * @param id the id of the actividadProyecto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actividad-proyectos/{id}")
    public ResponseEntity<Void> deleteActividadProyecto(@PathVariable Long id) {
        log.debug("REST request to delete ActividadProyecto : {}", id);
        actividadProyectoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
