package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Ambiente;
import co.edu.sena.ghostceet.repository.AmbienteRepository;
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
 * REST controller for managing Ambiente.
 */
@RestController
@RequestMapping("/api")
public class AmbienteResource {

    private final Logger log = LoggerFactory.getLogger(AmbienteResource.class);

    private static final String ENTITY_NAME = "ambiente";

    private final AmbienteRepository ambienteRepository;

    public AmbienteResource(AmbienteRepository ambienteRepository) {
        this.ambienteRepository = ambienteRepository;
    }

    /**
     * POST  /ambientes : Create a new ambiente.
     *
     * @param ambiente the ambiente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ambiente, or with status 400 (Bad Request) if the ambiente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ambientes")
    public ResponseEntity<Ambiente> createAmbiente(@Valid @RequestBody Ambiente ambiente) throws URISyntaxException {
        log.debug("REST request to save Ambiente : {}", ambiente);
        if (ambiente.getId() != null) {
            throw new BadRequestAlertException("A new ambiente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ambiente result = ambienteRepository.save(ambiente);
        return ResponseEntity.created(new URI("/api/ambientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ambientes : Updates an existing ambiente.
     *
     * @param ambiente the ambiente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ambiente,
     * or with status 400 (Bad Request) if the ambiente is not valid,
     * or with status 500 (Internal Server Error) if the ambiente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ambientes")
    public ResponseEntity<Ambiente> updateAmbiente(@Valid @RequestBody Ambiente ambiente) throws URISyntaxException {
        log.debug("REST request to update Ambiente : {}", ambiente);
        if (ambiente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ambiente result = ambienteRepository.save(ambiente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ambiente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ambientes : get all the ambientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ambientes in body
     */
    @GetMapping("/ambientes")
    public ResponseEntity<List<Ambiente>> getAllAmbientes(Pageable pageable) {
        log.debug("REST request to get a page of Ambientes");
        Page<Ambiente> page = ambienteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ambientes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ambientes/:id : get the "id" ambiente.
     *
     * @param id the id of the ambiente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ambiente, or with status 404 (Not Found)
     */
    @GetMapping("/ambientes/{id}")
    public ResponseEntity<Ambiente> getAmbiente(@PathVariable Long id) {
        log.debug("REST request to get Ambiente : {}", id);
        Optional<Ambiente> ambiente = ambienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ambiente);
    }

    /**
     * DELETE  /ambientes/:id : delete the "id" ambiente.
     *
     * @param id the id of the ambiente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ambientes/{id}")
    public ResponseEntity<Void> deleteAmbiente(@PathVariable Long id) {
        log.debug("REST request to delete Ambiente : {}", id);
        ambienteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
