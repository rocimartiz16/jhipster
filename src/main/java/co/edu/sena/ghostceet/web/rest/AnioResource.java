package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Anio;
import co.edu.sena.ghostceet.repository.AnioRepository;
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
 * REST controller for managing Anio.
 */
@RestController
@RequestMapping("/api")
public class AnioResource {

    private final Logger log = LoggerFactory.getLogger(AnioResource.class);

    private static final String ENTITY_NAME = "anio";

    private final AnioRepository anioRepository;

    public AnioResource(AnioRepository anioRepository) {
        this.anioRepository = anioRepository;
    }

    /**
     * POST  /anios : Create a new anio.
     *
     * @param anio the anio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anio, or with status 400 (Bad Request) if the anio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anios")
    public ResponseEntity<Anio> createAnio(@Valid @RequestBody Anio anio) throws URISyntaxException {
        log.debug("REST request to save Anio : {}", anio);
        if (anio.getId() != null) {
            throw new BadRequestAlertException("A new anio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Anio result = anioRepository.save(anio);
        return ResponseEntity.created(new URI("/api/anios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anios : Updates an existing anio.
     *
     * @param anio the anio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anio,
     * or with status 400 (Bad Request) if the anio is not valid,
     * or with status 500 (Internal Server Error) if the anio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anios")
    public ResponseEntity<Anio> updateAnio(@Valid @RequestBody Anio anio) throws URISyntaxException {
        log.debug("REST request to update Anio : {}", anio);
        if (anio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Anio result = anioRepository.save(anio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anios : get all the anios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of anios in body
     */
    @GetMapping("/anios")
    public ResponseEntity<List<Anio>> getAllAnios(Pageable pageable) {
        log.debug("REST request to get a page of Anios");
        Page<Anio> page = anioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/anios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /anios/:id : get the "id" anio.
     *
     * @param id the id of the anio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anio, or with status 404 (Not Found)
     */
    @GetMapping("/anios/{id}")
    public ResponseEntity<Anio> getAnio(@PathVariable Long id) {
        log.debug("REST request to get Anio : {}", id);
        Optional<Anio> anio = anioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(anio);
    }

    /**
     * DELETE  /anios/:id : delete the "id" anio.
     *
     * @param id the id of the anio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anios/{id}")
    public ResponseEntity<Void> deleteAnio(@PathVariable Long id) {
        log.debug("REST request to delete Anio : {}", id);
        anioRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
