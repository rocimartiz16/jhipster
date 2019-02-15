package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.JornadaInstructor;
import co.edu.sena.ghostceet.repository.JornadaInstructorRepository;
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
 * REST controller for managing JornadaInstructor.
 */
@RestController
@RequestMapping("/api")
public class JornadaInstructorResource {

    private final Logger log = LoggerFactory.getLogger(JornadaInstructorResource.class);

    private static final String ENTITY_NAME = "jornadaInstructor";

    private final JornadaInstructorRepository jornadaInstructorRepository;

    public JornadaInstructorResource(JornadaInstructorRepository jornadaInstructorRepository) {
        this.jornadaInstructorRepository = jornadaInstructorRepository;
    }

    /**
     * POST  /jornada-instructors : Create a new jornadaInstructor.
     *
     * @param jornadaInstructor the jornadaInstructor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jornadaInstructor, or with status 400 (Bad Request) if the jornadaInstructor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jornada-instructors")
    public ResponseEntity<JornadaInstructor> createJornadaInstructor(@Valid @RequestBody JornadaInstructor jornadaInstructor) throws URISyntaxException {
        log.debug("REST request to save JornadaInstructor : {}", jornadaInstructor);
        if (jornadaInstructor.getId() != null) {
            throw new BadRequestAlertException("A new jornadaInstructor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JornadaInstructor result = jornadaInstructorRepository.save(jornadaInstructor);
        return ResponseEntity.created(new URI("/api/jornada-instructors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jornada-instructors : Updates an existing jornadaInstructor.
     *
     * @param jornadaInstructor the jornadaInstructor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jornadaInstructor,
     * or with status 400 (Bad Request) if the jornadaInstructor is not valid,
     * or with status 500 (Internal Server Error) if the jornadaInstructor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jornada-instructors")
    public ResponseEntity<JornadaInstructor> updateJornadaInstructor(@Valid @RequestBody JornadaInstructor jornadaInstructor) throws URISyntaxException {
        log.debug("REST request to update JornadaInstructor : {}", jornadaInstructor);
        if (jornadaInstructor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JornadaInstructor result = jornadaInstructorRepository.save(jornadaInstructor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jornadaInstructor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jornada-instructors : get all the jornadaInstructors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jornadaInstructors in body
     */
    @GetMapping("/jornada-instructors")
    public ResponseEntity<List<JornadaInstructor>> getAllJornadaInstructors(Pageable pageable) {
        log.debug("REST request to get a page of JornadaInstructors");
        Page<JornadaInstructor> page = jornadaInstructorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jornada-instructors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /jornada-instructors/:id : get the "id" jornadaInstructor.
     *
     * @param id the id of the jornadaInstructor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jornadaInstructor, or with status 404 (Not Found)
     */
    @GetMapping("/jornada-instructors/{id}")
    public ResponseEntity<JornadaInstructor> getJornadaInstructor(@PathVariable Long id) {
        log.debug("REST request to get JornadaInstructor : {}", id);
        Optional<JornadaInstructor> jornadaInstructor = jornadaInstructorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jornadaInstructor);
    }

    /**
     * DELETE  /jornada-instructors/:id : delete the "id" jornadaInstructor.
     *
     * @param id the id of the jornadaInstructor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jornada-instructors/{id}")
    public ResponseEntity<Void> deleteJornadaInstructor(@PathVariable Long id) {
        log.debug("REST request to delete JornadaInstructor : {}", id);
        jornadaInstructorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
