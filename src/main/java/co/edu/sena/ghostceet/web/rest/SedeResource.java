package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Sede;
import co.edu.sena.ghostceet.repository.SedeRepository;
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
 * REST controller for managing Sede.
 */
@RestController
@RequestMapping("/api")
public class SedeResource {

    private final Logger log = LoggerFactory.getLogger(SedeResource.class);

    private static final String ENTITY_NAME = "sede";

    private final SedeRepository sedeRepository;

    public SedeResource(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
    }

    /**
     * POST  /sedes : Create a new sede.
     *
     * @param sede the sede to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sede, or with status 400 (Bad Request) if the sede has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sedes")
    public ResponseEntity<Sede> createSede(@Valid @RequestBody Sede sede) throws URISyntaxException {
        log.debug("REST request to save Sede : {}", sede);
        if (sede.getId() != null) {
            throw new BadRequestAlertException("A new sede cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sede result = sedeRepository.save(sede);
        return ResponseEntity.created(new URI("/api/sedes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sedes : Updates an existing sede.
     *
     * @param sede the sede to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sede,
     * or with status 400 (Bad Request) if the sede is not valid,
     * or with status 500 (Internal Server Error) if the sede couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sedes")
    public ResponseEntity<Sede> updateSede(@Valid @RequestBody Sede sede) throws URISyntaxException {
        log.debug("REST request to update Sede : {}", sede);
        if (sede.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sede result = sedeRepository.save(sede);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sede.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sedes : get all the sedes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sedes in body
     */
    @GetMapping("/sedes")
    public List<Sede> getAllSedes() {
        log.debug("REST request to get all Sedes");
        return sedeRepository.findAll();
    }

    /**
     * GET  /sedes/:id : get the "id" sede.
     *
     * @param id the id of the sede to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sede, or with status 404 (Not Found)
     */
    @GetMapping("/sedes/{id}")
    public ResponseEntity<Sede> getSede(@PathVariable Long id) {
        log.debug("REST request to get Sede : {}", id);
        Optional<Sede> sede = sedeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sede);
    }

    /**
     * DELETE  /sedes/:id : delete the "id" sede.
     *
     * @param id the id of the sede to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sedes/{id}")
    public ResponseEntity<Void> deleteSede(@PathVariable Long id) {
        log.debug("REST request to delete Sede : {}", id);
        sedeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
