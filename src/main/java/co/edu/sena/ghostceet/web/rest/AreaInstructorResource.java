package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.AreaInstructor;
import co.edu.sena.ghostceet.repository.AreaInstructorRepository;
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
 * REST controller for managing AreaInstructor.
 */
@RestController
@RequestMapping("/api")
public class AreaInstructorResource {

    private final Logger log = LoggerFactory.getLogger(AreaInstructorResource.class);

    private static final String ENTITY_NAME = "areaInstructor";

    private final AreaInstructorRepository areaInstructorRepository;

    public AreaInstructorResource(AreaInstructorRepository areaInstructorRepository) {
        this.areaInstructorRepository = areaInstructorRepository;
    }

    /**
     * POST  /area-instructors : Create a new areaInstructor.
     *
     * @param areaInstructor the areaInstructor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new areaInstructor, or with status 400 (Bad Request) if the areaInstructor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/area-instructors")
    public ResponseEntity<AreaInstructor> createAreaInstructor(@Valid @RequestBody AreaInstructor areaInstructor) throws URISyntaxException {
        log.debug("REST request to save AreaInstructor : {}", areaInstructor);
        if (areaInstructor.getId() != null) {
            throw new BadRequestAlertException("A new areaInstructor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaInstructor result = areaInstructorRepository.save(areaInstructor);
        return ResponseEntity.created(new URI("/api/area-instructors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /area-instructors : Updates an existing areaInstructor.
     *
     * @param areaInstructor the areaInstructor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated areaInstructor,
     * or with status 400 (Bad Request) if the areaInstructor is not valid,
     * or with status 500 (Internal Server Error) if the areaInstructor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/area-instructors")
    public ResponseEntity<AreaInstructor> updateAreaInstructor(@Valid @RequestBody AreaInstructor areaInstructor) throws URISyntaxException {
        log.debug("REST request to update AreaInstructor : {}", areaInstructor);
        if (areaInstructor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AreaInstructor result = areaInstructorRepository.save(areaInstructor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, areaInstructor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /area-instructors : get all the areaInstructors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of areaInstructors in body
     */
    @GetMapping("/area-instructors")
    public List<AreaInstructor> getAllAreaInstructors() {
        log.debug("REST request to get all AreaInstructors");
        return areaInstructorRepository.findAll();
    }

    /**
     * GET  /area-instructors/:id : get the "id" areaInstructor.
     *
     * @param id the id of the areaInstructor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the areaInstructor, or with status 404 (Not Found)
     */
    @GetMapping("/area-instructors/{id}")
    public ResponseEntity<AreaInstructor> getAreaInstructor(@PathVariable Long id) {
        log.debug("REST request to get AreaInstructor : {}", id);
        Optional<AreaInstructor> areaInstructor = areaInstructorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(areaInstructor);
    }

    /**
     * DELETE  /area-instructors/:id : delete the "id" areaInstructor.
     *
     * @param id the id of the areaInstructor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/area-instructors/{id}")
    public ResponseEntity<Void> deleteAreaInstructor(@PathVariable Long id) {
        log.debug("REST request to delete AreaInstructor : {}", id);
        areaInstructorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
