package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.PlaneacionTrimestre;
import co.edu.sena.ghostceet.repository.PlaneacionTrimestreRepository;
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
 * REST controller for managing PlaneacionTrimestre.
 */
@RestController
@RequestMapping("/api")
public class PlaneacionTrimestreResource {

    private final Logger log = LoggerFactory.getLogger(PlaneacionTrimestreResource.class);

    private static final String ENTITY_NAME = "planeacionTrimestre";

    private final PlaneacionTrimestreRepository planeacionTrimestreRepository;

    public PlaneacionTrimestreResource(PlaneacionTrimestreRepository planeacionTrimestreRepository) {
        this.planeacionTrimestreRepository = planeacionTrimestreRepository;
    }

    /**
     * POST  /planeacion-trimestres : Create a new planeacionTrimestre.
     *
     * @param planeacionTrimestre the planeacionTrimestre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planeacionTrimestre, or with status 400 (Bad Request) if the planeacionTrimestre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planeacion-trimestres")
    public ResponseEntity<PlaneacionTrimestre> createPlaneacionTrimestre(@Valid @RequestBody PlaneacionTrimestre planeacionTrimestre) throws URISyntaxException {
        log.debug("REST request to save PlaneacionTrimestre : {}", planeacionTrimestre);
        if (planeacionTrimestre.getId() != null) {
            throw new BadRequestAlertException("A new planeacionTrimestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaneacionTrimestre result = planeacionTrimestreRepository.save(planeacionTrimestre);
        return ResponseEntity.created(new URI("/api/planeacion-trimestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planeacion-trimestres : Updates an existing planeacionTrimestre.
     *
     * @param planeacionTrimestre the planeacionTrimestre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planeacionTrimestre,
     * or with status 400 (Bad Request) if the planeacionTrimestre is not valid,
     * or with status 500 (Internal Server Error) if the planeacionTrimestre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planeacion-trimestres")
    public ResponseEntity<PlaneacionTrimestre> updatePlaneacionTrimestre(@Valid @RequestBody PlaneacionTrimestre planeacionTrimestre) throws URISyntaxException {
        log.debug("REST request to update PlaneacionTrimestre : {}", planeacionTrimestre);
        if (planeacionTrimestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlaneacionTrimestre result = planeacionTrimestreRepository.save(planeacionTrimestre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planeacionTrimestre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planeacion-trimestres : get all the planeacionTrimestres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planeacionTrimestres in body
     */
    @GetMapping("/planeacion-trimestres")
    public List<PlaneacionTrimestre> getAllPlaneacionTrimestres() {
        log.debug("REST request to get all PlaneacionTrimestres");
        return planeacionTrimestreRepository.findAll();
    }

    /**
     * GET  /planeacion-trimestres/:id : get the "id" planeacionTrimestre.
     *
     * @param id the id of the planeacionTrimestre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planeacionTrimestre, or with status 404 (Not Found)
     */
    @GetMapping("/planeacion-trimestres/{id}")
    public ResponseEntity<PlaneacionTrimestre> getPlaneacionTrimestre(@PathVariable Long id) {
        log.debug("REST request to get PlaneacionTrimestre : {}", id);
        Optional<PlaneacionTrimestre> planeacionTrimestre = planeacionTrimestreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planeacionTrimestre);
    }

    /**
     * DELETE  /planeacion-trimestres/:id : delete the "id" planeacionTrimestre.
     *
     * @param id the id of the planeacionTrimestre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planeacion-trimestres/{id}")
    public ResponseEntity<Void> deletePlaneacionTrimestre(@PathVariable Long id) {
        log.debug("REST request to delete PlaneacionTrimestre : {}", id);
        planeacionTrimestreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
