package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Instructor;
import co.edu.sena.ghostceet.repository.InstructorRepository;
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
 * REST controller for managing Instructor.
 */
@RestController
@RequestMapping("/api")
public class InstructorResource {

    private final Logger log = LoggerFactory.getLogger(InstructorResource.class);

    private static final String ENTITY_NAME = "instructor";

    private final InstructorRepository instructorRepository;

    public InstructorResource(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    /**
     * POST  /instructors : Create a new instructor.
     *
     * @param instructor the instructor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new instructor, or with status 400 (Bad Request) if the instructor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/instructors")
    public ResponseEntity<Instructor> createInstructor(@Valid @RequestBody Instructor instructor) throws URISyntaxException {
        log.debug("REST request to save Instructor : {}", instructor);
        if (instructor.getId() != null) {
            throw new BadRequestAlertException("A new instructor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Instructor result = instructorRepository.save(instructor);
        return ResponseEntity.created(new URI("/api/instructors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instructors : Updates an existing instructor.
     *
     * @param instructor the instructor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated instructor,
     * or with status 400 (Bad Request) if the instructor is not valid,
     * or with status 500 (Internal Server Error) if the instructor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/instructors")
    public ResponseEntity<Instructor> updateInstructor(@Valid @RequestBody Instructor instructor) throws URISyntaxException {
        log.debug("REST request to update Instructor : {}", instructor);
        if (instructor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Instructor result = instructorRepository.save(instructor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, instructor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instructors : get all the instructors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of instructors in body
     */
    @GetMapping("/instructors")
    public ResponseEntity<List<Instructor>> getAllInstructors(Pageable pageable) {
        log.debug("REST request to get a page of Instructors");
        Page<Instructor> page = instructorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instructors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /instructors/:id : get the "id" instructor.
     *
     * @param id the id of the instructor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the instructor, or with status 404 (Not Found)
     */
    @GetMapping("/instructors/{id}")
    public ResponseEntity<Instructor> getInstructor(@PathVariable Long id) {
        log.debug("REST request to get Instructor : {}", id);
        Optional<Instructor> instructor = instructorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(instructor);
    }

    /**
     * DELETE  /instructors/:id : delete the "id" instructor.
     *
     * @param id the id of the instructor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/instructors/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        log.debug("REST request to delete Instructor : {}", id);
        instructorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
