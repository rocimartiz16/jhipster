package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.VinculacionInstructor;
import co.edu.sena.ghostceet.repository.VinculacionInstructorRepository;
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
 * REST controller for managing VinculacionInstructor.
 */
@RestController
@RequestMapping("/api")
public class VinculacionInstructorResource {

    private final Logger log = LoggerFactory.getLogger(VinculacionInstructorResource.class);

    private static final String ENTITY_NAME = "vinculacionInstructor";

    private final VinculacionInstructorRepository vinculacionInstructorRepository;

    public VinculacionInstructorResource(VinculacionInstructorRepository vinculacionInstructorRepository) {
        this.vinculacionInstructorRepository = vinculacionInstructorRepository;
    }

    /**
     * POST  /vinculacion-instructors : Create a new vinculacionInstructor.
     *
     * @param vinculacionInstructor the vinculacionInstructor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vinculacionInstructor, or with status 400 (Bad Request) if the vinculacionInstructor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vinculacion-instructors")
    public ResponseEntity<VinculacionInstructor> createVinculacionInstructor(@Valid @RequestBody VinculacionInstructor vinculacionInstructor) throws URISyntaxException {
        log.debug("REST request to save VinculacionInstructor : {}", vinculacionInstructor);
        if (vinculacionInstructor.getId() != null) {
            throw new BadRequestAlertException("A new vinculacionInstructor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VinculacionInstructor result = vinculacionInstructorRepository.save(vinculacionInstructor);
        return ResponseEntity.created(new URI("/api/vinculacion-instructors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vinculacion-instructors : Updates an existing vinculacionInstructor.
     *
     * @param vinculacionInstructor the vinculacionInstructor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vinculacionInstructor,
     * or with status 400 (Bad Request) if the vinculacionInstructor is not valid,
     * or with status 500 (Internal Server Error) if the vinculacionInstructor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vinculacion-instructors")
    public ResponseEntity<VinculacionInstructor> updateVinculacionInstructor(@Valid @RequestBody VinculacionInstructor vinculacionInstructor) throws URISyntaxException {
        log.debug("REST request to update VinculacionInstructor : {}", vinculacionInstructor);
        if (vinculacionInstructor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VinculacionInstructor result = vinculacionInstructorRepository.save(vinculacionInstructor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vinculacionInstructor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vinculacion-instructors : get all the vinculacionInstructors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vinculacionInstructors in body
     */
    @GetMapping("/vinculacion-instructors")
    public ResponseEntity<List<VinculacionInstructor>> getAllVinculacionInstructors(Pageable pageable) {
        log.debug("REST request to get a page of VinculacionInstructors");
        Page<VinculacionInstructor> page = vinculacionInstructorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vinculacion-instructors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /vinculacion-instructors/:id : get the "id" vinculacionInstructor.
     *
     * @param id the id of the vinculacionInstructor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vinculacionInstructor, or with status 404 (Not Found)
     */
    @GetMapping("/vinculacion-instructors/{id}")
    public ResponseEntity<VinculacionInstructor> getVinculacionInstructor(@PathVariable Long id) {
        log.debug("REST request to get VinculacionInstructor : {}", id);
        Optional<VinculacionInstructor> vinculacionInstructor = vinculacionInstructorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vinculacionInstructor);
    }

    /**
     * DELETE  /vinculacion-instructors/:id : delete the "id" vinculacionInstructor.
     *
     * @param id the id of the vinculacionInstructor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vinculacion-instructors/{id}")
    public ResponseEntity<Void> deleteVinculacionInstructor(@PathVariable Long id) {
        log.debug("REST request to delete VinculacionInstructor : {}", id);
        vinculacionInstructorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
