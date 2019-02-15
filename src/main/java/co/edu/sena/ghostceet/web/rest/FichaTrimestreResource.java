package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.FichaTrimestre;
import co.edu.sena.ghostceet.repository.FichaTrimestreRepository;
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
 * REST controller for managing FichaTrimestre.
 */
@RestController
@RequestMapping("/api")
public class FichaTrimestreResource {

    private final Logger log = LoggerFactory.getLogger(FichaTrimestreResource.class);

    private static final String ENTITY_NAME = "fichaTrimestre";

    private final FichaTrimestreRepository fichaTrimestreRepository;

    public FichaTrimestreResource(FichaTrimestreRepository fichaTrimestreRepository) {
        this.fichaTrimestreRepository = fichaTrimestreRepository;
    }

    /**
     * POST  /ficha-trimestres : Create a new fichaTrimestre.
     *
     * @param fichaTrimestre the fichaTrimestre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fichaTrimestre, or with status 400 (Bad Request) if the fichaTrimestre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ficha-trimestres")
    public ResponseEntity<FichaTrimestre> createFichaTrimestre(@Valid @RequestBody FichaTrimestre fichaTrimestre) throws URISyntaxException {
        log.debug("REST request to save FichaTrimestre : {}", fichaTrimestre);
        if (fichaTrimestre.getId() != null) {
            throw new BadRequestAlertException("A new fichaTrimestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaTrimestre result = fichaTrimestreRepository.save(fichaTrimestre);
        return ResponseEntity.created(new URI("/api/ficha-trimestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ficha-trimestres : Updates an existing fichaTrimestre.
     *
     * @param fichaTrimestre the fichaTrimestre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fichaTrimestre,
     * or with status 400 (Bad Request) if the fichaTrimestre is not valid,
     * or with status 500 (Internal Server Error) if the fichaTrimestre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ficha-trimestres")
    public ResponseEntity<FichaTrimestre> updateFichaTrimestre(@Valid @RequestBody FichaTrimestre fichaTrimestre) throws URISyntaxException {
        log.debug("REST request to update FichaTrimestre : {}", fichaTrimestre);
        if (fichaTrimestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichaTrimestre result = fichaTrimestreRepository.save(fichaTrimestre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fichaTrimestre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ficha-trimestres : get all the fichaTrimestres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fichaTrimestres in body
     */
    @GetMapping("/ficha-trimestres")
    public List<FichaTrimestre> getAllFichaTrimestres() {
        log.debug("REST request to get all FichaTrimestres");
        return fichaTrimestreRepository.findAll();
    }

    /**
     * GET  /ficha-trimestres/:id : get the "id" fichaTrimestre.
     *
     * @param id the id of the fichaTrimestre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fichaTrimestre, or with status 404 (Not Found)
     */
    @GetMapping("/ficha-trimestres/{id}")
    public ResponseEntity<FichaTrimestre> getFichaTrimestre(@PathVariable Long id) {
        log.debug("REST request to get FichaTrimestre : {}", id);
        Optional<FichaTrimestre> fichaTrimestre = fichaTrimestreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fichaTrimestre);
    }

    /**
     * DELETE  /ficha-trimestres/:id : delete the "id" fichaTrimestre.
     *
     * @param id the id of the fichaTrimestre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ficha-trimestres/{id}")
    public ResponseEntity<Void> deleteFichaTrimestre(@PathVariable Long id) {
        log.debug("REST request to delete FichaTrimestre : {}", id);
        fichaTrimestreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
