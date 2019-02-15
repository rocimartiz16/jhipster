package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Trimestre;
import co.edu.sena.ghostceet.repository.TrimestreRepository;
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
 * REST controller for managing Trimestre.
 */
@RestController
@RequestMapping("/api")
public class TrimestreResource {

    private final Logger log = LoggerFactory.getLogger(TrimestreResource.class);

    private static final String ENTITY_NAME = "trimestre";

    private final TrimestreRepository trimestreRepository;

    public TrimestreResource(TrimestreRepository trimestreRepository) {
        this.trimestreRepository = trimestreRepository;
    }

    /**
     * POST  /trimestres : Create a new trimestre.
     *
     * @param trimestre the trimestre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trimestre, or with status 400 (Bad Request) if the trimestre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trimestres")
    public ResponseEntity<Trimestre> createTrimestre(@Valid @RequestBody Trimestre trimestre) throws URISyntaxException {
        log.debug("REST request to save Trimestre : {}", trimestre);
        if (trimestre.getId() != null) {
            throw new BadRequestAlertException("A new trimestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trimestre result = trimestreRepository.save(trimestre);
        return ResponseEntity.created(new URI("/api/trimestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trimestres : Updates an existing trimestre.
     *
     * @param trimestre the trimestre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trimestre,
     * or with status 400 (Bad Request) if the trimestre is not valid,
     * or with status 500 (Internal Server Error) if the trimestre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trimestres")
    public ResponseEntity<Trimestre> updateTrimestre(@Valid @RequestBody Trimestre trimestre) throws URISyntaxException {
        log.debug("REST request to update Trimestre : {}", trimestre);
        if (trimestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trimestre result = trimestreRepository.save(trimestre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trimestre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trimestres : get all the trimestres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trimestres in body
     */
    @GetMapping("/trimestres")
    public ResponseEntity<List<Trimestre>> getAllTrimestres(Pageable pageable) {
        log.debug("REST request to get a page of Trimestres");
        Page<Trimestre> page = trimestreRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trimestres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /trimestres/:id : get the "id" trimestre.
     *
     * @param id the id of the trimestre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trimestre, or with status 404 (Not Found)
     */
    @GetMapping("/trimestres/{id}")
    public ResponseEntity<Trimestre> getTrimestre(@PathVariable Long id) {
        log.debug("REST request to get Trimestre : {}", id);
        Optional<Trimestre> trimestre = trimestreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trimestre);
    }

    /**
     * DELETE  /trimestres/:id : delete the "id" trimestre.
     *
     * @param id the id of the trimestre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trimestres/{id}")
    public ResponseEntity<Void> deleteTrimestre(@PathVariable Long id) {
        log.debug("REST request to delete Trimestre : {}", id);
        trimestreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
