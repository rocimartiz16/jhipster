package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Ficha;
import co.edu.sena.ghostceet.repository.FichaRepository;
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
 * REST controller for managing Ficha.
 */
@RestController
@RequestMapping("/api")
public class FichaResource {

    private final Logger log = LoggerFactory.getLogger(FichaResource.class);

    private static final String ENTITY_NAME = "ficha";

    private final FichaRepository fichaRepository;

    public FichaResource(FichaRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }

    /**
     * POST  /fichas : Create a new ficha.
     *
     * @param ficha the ficha to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ficha, or with status 400 (Bad Request) if the ficha has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fichas")
    public ResponseEntity<Ficha> createFicha(@Valid @RequestBody Ficha ficha) throws URISyntaxException {
        log.debug("REST request to save Ficha : {}", ficha);
        if (ficha.getId() != null) {
            throw new BadRequestAlertException("A new ficha cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ficha result = fichaRepository.save(ficha);
        return ResponseEntity.created(new URI("/api/fichas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fichas : Updates an existing ficha.
     *
     * @param ficha the ficha to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ficha,
     * or with status 400 (Bad Request) if the ficha is not valid,
     * or with status 500 (Internal Server Error) if the ficha couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fichas")
    public ResponseEntity<Ficha> updateFicha(@Valid @RequestBody Ficha ficha) throws URISyntaxException {
        log.debug("REST request to update Ficha : {}", ficha);
        if (ficha.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ficha result = fichaRepository.save(ficha);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ficha.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fichas : get all the fichas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fichas in body
     */
    @GetMapping("/fichas")
    public List<Ficha> getAllFichas() {
        log.debug("REST request to get all Fichas");
        return fichaRepository.findAll();
    }

    /**
     * GET  /fichas/:id : get the "id" ficha.
     *
     * @param id the id of the ficha to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ficha, or with status 404 (Not Found)
     */
    @GetMapping("/fichas/{id}")
    public ResponseEntity<Ficha> getFicha(@PathVariable Long id) {
        log.debug("REST request to get Ficha : {}", id);
        Optional<Ficha> ficha = fichaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ficha);
    }

    /**
     * DELETE  /fichas/:id : delete the "id" ficha.
     *
     * @param id the id of the ficha to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fichas/{id}")
    public ResponseEntity<Void> deleteFicha(@PathVariable Long id) {
        log.debug("REST request to delete Ficha : {}", id);
        fichaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
