package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Competencia;
import co.edu.sena.ghostceet.repository.CompetenciaRepository;
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
 * REST controller for managing Competencia.
 */
@RestController
@RequestMapping("/api")
public class CompetenciaResource {

    private final Logger log = LoggerFactory.getLogger(CompetenciaResource.class);

    private static final String ENTITY_NAME = "competencia";

    private final CompetenciaRepository competenciaRepository;

    public CompetenciaResource(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository;
    }

    /**
     * POST  /competencias : Create a new competencia.
     *
     * @param competencia the competencia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new competencia, or with status 400 (Bad Request) if the competencia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/competencias")
    public ResponseEntity<Competencia> createCompetencia(@Valid @RequestBody Competencia competencia) throws URISyntaxException {
        log.debug("REST request to save Competencia : {}", competencia);
        if (competencia.getId() != null) {
            throw new BadRequestAlertException("A new competencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competencia result = competenciaRepository.save(competencia);
        return ResponseEntity.created(new URI("/api/competencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /competencias : Updates an existing competencia.
     *
     * @param competencia the competencia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated competencia,
     * or with status 400 (Bad Request) if the competencia is not valid,
     * or with status 500 (Internal Server Error) if the competencia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/competencias")
    public ResponseEntity<Competencia> updateCompetencia(@Valid @RequestBody Competencia competencia) throws URISyntaxException {
        log.debug("REST request to update Competencia : {}", competencia);
        if (competencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Competencia result = competenciaRepository.save(competencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, competencia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /competencias : get all the competencias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of competencias in body
     */
    @GetMapping("/competencias")
    public ResponseEntity<List<Competencia>> getAllCompetencias(Pageable pageable) {
        log.debug("REST request to get a page of Competencias");
        Page<Competencia> page = competenciaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/competencias");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /competencias/:id : get the "id" competencia.
     *
     * @param id the id of the competencia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the competencia, or with status 404 (Not Found)
     */
    @GetMapping("/competencias/{id}")
    public ResponseEntity<Competencia> getCompetencia(@PathVariable Long id) {
        log.debug("REST request to get Competencia : {}", id);
        Optional<Competencia> competencia = competenciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competencia);
    }

    /**
     * DELETE  /competencias/:id : delete the "id" competencia.
     *
     * @param id the id of the competencia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/competencias/{id}")
    public ResponseEntity<Void> deleteCompetencia(@PathVariable Long id) {
        log.debug("REST request to delete Competencia : {}", id);
        competenciaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
