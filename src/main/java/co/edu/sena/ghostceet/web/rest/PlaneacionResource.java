package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Planeacion;
import co.edu.sena.ghostceet.repository.PlaneacionRepository;
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
 * REST controller for managing Planeacion.
 */
@RestController
@RequestMapping("/api")
public class PlaneacionResource {

    private final Logger log = LoggerFactory.getLogger(PlaneacionResource.class);

    private static final String ENTITY_NAME = "planeacion";

    private final PlaneacionRepository planeacionRepository;

    public PlaneacionResource(PlaneacionRepository planeacionRepository) {
        this.planeacionRepository = planeacionRepository;
    }

    /**
     * POST  /planeacions : Create a new planeacion.
     *
     * @param planeacion the planeacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planeacion, or with status 400 (Bad Request) if the planeacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planeacions")
    public ResponseEntity<Planeacion> createPlaneacion(@Valid @RequestBody Planeacion planeacion) throws URISyntaxException {
        log.debug("REST request to save Planeacion : {}", planeacion);
        if (planeacion.getId() != null) {
            throw new BadRequestAlertException("A new planeacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Planeacion result = planeacionRepository.save(planeacion);
        return ResponseEntity.created(new URI("/api/planeacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planeacions : Updates an existing planeacion.
     *
     * @param planeacion the planeacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planeacion,
     * or with status 400 (Bad Request) if the planeacion is not valid,
     * or with status 500 (Internal Server Error) if the planeacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planeacions")
    public ResponseEntity<Planeacion> updatePlaneacion(@Valid @RequestBody Planeacion planeacion) throws URISyntaxException {
        log.debug("REST request to update Planeacion : {}", planeacion);
        if (planeacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Planeacion result = planeacionRepository.save(planeacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planeacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planeacions : get all the planeacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planeacions in body
     */
    @GetMapping("/planeacions")
    public List<Planeacion> getAllPlaneacions() {
        log.debug("REST request to get all Planeacions");
        return planeacionRepository.findAll();
    }

    /**
     * GET  /planeacions/:id : get the "id" planeacion.
     *
     * @param id the id of the planeacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planeacion, or with status 404 (Not Found)
     */
    @GetMapping("/planeacions/{id}")
    public ResponseEntity<Planeacion> getPlaneacion(@PathVariable Long id) {
        log.debug("REST request to get Planeacion : {}", id);
        Optional<Planeacion> planeacion = planeacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planeacion);
    }

    /**
     * DELETE  /planeacions/:id : delete the "id" planeacion.
     *
     * @param id the id of the planeacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planeacions/{id}")
    public ResponseEntity<Void> deletePlaneacion(@PathVariable Long id) {
        log.debug("REST request to delete Planeacion : {}", id);
        planeacionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
