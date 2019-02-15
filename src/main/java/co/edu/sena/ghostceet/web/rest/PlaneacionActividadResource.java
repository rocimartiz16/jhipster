package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.PlaneacionActividad;
import co.edu.sena.ghostceet.repository.PlaneacionActividadRepository;
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
 * REST controller for managing PlaneacionActividad.
 */
@RestController
@RequestMapping("/api")
public class PlaneacionActividadResource {

    private final Logger log = LoggerFactory.getLogger(PlaneacionActividadResource.class);

    private static final String ENTITY_NAME = "planeacionActividad";

    private final PlaneacionActividadRepository planeacionActividadRepository;

    public PlaneacionActividadResource(PlaneacionActividadRepository planeacionActividadRepository) {
        this.planeacionActividadRepository = planeacionActividadRepository;
    }

    /**
     * POST  /planeacion-actividads : Create a new planeacionActividad.
     *
     * @param planeacionActividad the planeacionActividad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planeacionActividad, or with status 400 (Bad Request) if the planeacionActividad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planeacion-actividads")
    public ResponseEntity<PlaneacionActividad> createPlaneacionActividad(@Valid @RequestBody PlaneacionActividad planeacionActividad) throws URISyntaxException {
        log.debug("REST request to save PlaneacionActividad : {}", planeacionActividad);
        if (planeacionActividad.getId() != null) {
            throw new BadRequestAlertException("A new planeacionActividad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaneacionActividad result = planeacionActividadRepository.save(planeacionActividad);
        return ResponseEntity.created(new URI("/api/planeacion-actividads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planeacion-actividads : Updates an existing planeacionActividad.
     *
     * @param planeacionActividad the planeacionActividad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planeacionActividad,
     * or with status 400 (Bad Request) if the planeacionActividad is not valid,
     * or with status 500 (Internal Server Error) if the planeacionActividad couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planeacion-actividads")
    public ResponseEntity<PlaneacionActividad> updatePlaneacionActividad(@Valid @RequestBody PlaneacionActividad planeacionActividad) throws URISyntaxException {
        log.debug("REST request to update PlaneacionActividad : {}", planeacionActividad);
        if (planeacionActividad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlaneacionActividad result = planeacionActividadRepository.save(planeacionActividad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planeacionActividad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planeacion-actividads : get all the planeacionActividads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planeacionActividads in body
     */
    @GetMapping("/planeacion-actividads")
    public List<PlaneacionActividad> getAllPlaneacionActividads() {
        log.debug("REST request to get all PlaneacionActividads");
        return planeacionActividadRepository.findAll();
    }

    /**
     * GET  /planeacion-actividads/:id : get the "id" planeacionActividad.
     *
     * @param id the id of the planeacionActividad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planeacionActividad, or with status 404 (Not Found)
     */
    @GetMapping("/planeacion-actividads/{id}")
    public ResponseEntity<PlaneacionActividad> getPlaneacionActividad(@PathVariable Long id) {
        log.debug("REST request to get PlaneacionActividad : {}", id);
        Optional<PlaneacionActividad> planeacionActividad = planeacionActividadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planeacionActividad);
    }

    /**
     * DELETE  /planeacion-actividads/:id : delete the "id" planeacionActividad.
     *
     * @param id the id of the planeacionActividad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planeacion-actividads/{id}")
    public ResponseEntity<Void> deletePlaneacionActividad(@PathVariable Long id) {
        log.debug("REST request to delete PlaneacionActividad : {}", id);
        planeacionActividadRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
