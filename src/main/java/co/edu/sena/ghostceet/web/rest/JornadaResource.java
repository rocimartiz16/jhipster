package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Jornada;
import co.edu.sena.ghostceet.repository.JornadaRepository;
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
 * REST controller for managing Jornada.
 */
@RestController
@RequestMapping("/api")
public class JornadaResource {

    private final Logger log = LoggerFactory.getLogger(JornadaResource.class);

    private static final String ENTITY_NAME = "jornada";

    private final JornadaRepository jornadaRepository;

    public JornadaResource(JornadaRepository jornadaRepository) {
        this.jornadaRepository = jornadaRepository;
    }

    /**
     * POST  /jornadas : Create a new jornada.
     *
     * @param jornada the jornada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jornada, or with status 400 (Bad Request) if the jornada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jornadas")
    public ResponseEntity<Jornada> createJornada(@Valid @RequestBody Jornada jornada) throws URISyntaxException {
        log.debug("REST request to save Jornada : {}", jornada);
        if (jornada.getId() != null) {
            throw new BadRequestAlertException("A new jornada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jornada result = jornadaRepository.save(jornada);
        return ResponseEntity.created(new URI("/api/jornadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jornadas : Updates an existing jornada.
     *
     * @param jornada the jornada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jornada,
     * or with status 400 (Bad Request) if the jornada is not valid,
     * or with status 500 (Internal Server Error) if the jornada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jornadas")
    public ResponseEntity<Jornada> updateJornada(@Valid @RequestBody Jornada jornada) throws URISyntaxException {
        log.debug("REST request to update Jornada : {}", jornada);
        if (jornada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Jornada result = jornadaRepository.save(jornada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jornada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jornadas : get all the jornadas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jornadas in body
     */
    @GetMapping("/jornadas")
    public ResponseEntity<List<Jornada>> getAllJornadas(Pageable pageable) {
        log.debug("REST request to get a page of Jornadas");
        Page<Jornada> page = jornadaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jornadas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /jornadas/:id : get the "id" jornada.
     *
     * @param id the id of the jornada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jornada, or with status 404 (Not Found)
     */
    @GetMapping("/jornadas/{id}")
    public ResponseEntity<Jornada> getJornada(@PathVariable Long id) {
        log.debug("REST request to get Jornada : {}", id);
        Optional<Jornada> jornada = jornadaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jornada);
    }

    /**
     * DELETE  /jornadas/:id : delete the "id" jornada.
     *
     * @param id the id of the jornada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jornadas/{id}")
    public ResponseEntity<Void> deleteJornada(@PathVariable Long id) {
        log.debug("REST request to delete Jornada : {}", id);
        jornadaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
