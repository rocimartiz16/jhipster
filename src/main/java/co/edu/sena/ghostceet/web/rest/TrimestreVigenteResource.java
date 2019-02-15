package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.TrimestreVigente;
import co.edu.sena.ghostceet.repository.TrimestreVigenteRepository;
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
 * REST controller for managing TrimestreVigente.
 */
@RestController
@RequestMapping("/api")
public class TrimestreVigenteResource {

    private final Logger log = LoggerFactory.getLogger(TrimestreVigenteResource.class);

    private static final String ENTITY_NAME = "trimestreVigente";

    private final TrimestreVigenteRepository trimestreVigenteRepository;

    public TrimestreVigenteResource(TrimestreVigenteRepository trimestreVigenteRepository) {
        this.trimestreVigenteRepository = trimestreVigenteRepository;
    }

    /**
     * POST  /trimestre-vigentes : Create a new trimestreVigente.
     *
     * @param trimestreVigente the trimestreVigente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trimestreVigente, or with status 400 (Bad Request) if the trimestreVigente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trimestre-vigentes")
    public ResponseEntity<TrimestreVigente> createTrimestreVigente(@Valid @RequestBody TrimestreVigente trimestreVigente) throws URISyntaxException {
        log.debug("REST request to save TrimestreVigente : {}", trimestreVigente);
        if (trimestreVigente.getId() != null) {
            throw new BadRequestAlertException("A new trimestreVigente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrimestreVigente result = trimestreVigenteRepository.save(trimestreVigente);
        return ResponseEntity.created(new URI("/api/trimestre-vigentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trimestre-vigentes : Updates an existing trimestreVigente.
     *
     * @param trimestreVigente the trimestreVigente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trimestreVigente,
     * or with status 400 (Bad Request) if the trimestreVigente is not valid,
     * or with status 500 (Internal Server Error) if the trimestreVigente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trimestre-vigentes")
    public ResponseEntity<TrimestreVigente> updateTrimestreVigente(@Valid @RequestBody TrimestreVigente trimestreVigente) throws URISyntaxException {
        log.debug("REST request to update TrimestreVigente : {}", trimestreVigente);
        if (trimestreVigente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrimestreVigente result = trimestreVigenteRepository.save(trimestreVigente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trimestreVigente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trimestre-vigentes : get all the trimestreVigentes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trimestreVigentes in body
     */
    @GetMapping("/trimestre-vigentes")
    public ResponseEntity<List<TrimestreVigente>> getAllTrimestreVigentes(Pageable pageable) {
        log.debug("REST request to get a page of TrimestreVigentes");
        Page<TrimestreVigente> page = trimestreVigenteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trimestre-vigentes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /trimestre-vigentes/:id : get the "id" trimestreVigente.
     *
     * @param id the id of the trimestreVigente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trimestreVigente, or with status 404 (Not Found)
     */
    @GetMapping("/trimestre-vigentes/{id}")
    public ResponseEntity<TrimestreVigente> getTrimestreVigente(@PathVariable Long id) {
        log.debug("REST request to get TrimestreVigente : {}", id);
        Optional<TrimestreVigente> trimestreVigente = trimestreVigenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trimestreVigente);
    }

    /**
     * DELETE  /trimestre-vigentes/:id : delete the "id" trimestreVigente.
     *
     * @param id the id of the trimestreVigente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trimestre-vigentes/{id}")
    public ResponseEntity<Void> deleteTrimestreVigente(@PathVariable Long id) {
        log.debug("REST request to delete TrimestreVigente : {}", id);
        trimestreVigenteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
