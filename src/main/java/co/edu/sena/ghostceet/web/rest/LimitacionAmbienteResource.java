package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.LimitacionAmbiente;
import co.edu.sena.ghostceet.repository.LimitacionAmbienteRepository;
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
 * REST controller for managing LimitacionAmbiente.
 */
@RestController
@RequestMapping("/api")
public class LimitacionAmbienteResource {

    private final Logger log = LoggerFactory.getLogger(LimitacionAmbienteResource.class);

    private static final String ENTITY_NAME = "limitacionAmbiente";

    private final LimitacionAmbienteRepository limitacionAmbienteRepository;

    public LimitacionAmbienteResource(LimitacionAmbienteRepository limitacionAmbienteRepository) {
        this.limitacionAmbienteRepository = limitacionAmbienteRepository;
    }

    /**
     * POST  /limitacion-ambientes : Create a new limitacionAmbiente.
     *
     * @param limitacionAmbiente the limitacionAmbiente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new limitacionAmbiente, or with status 400 (Bad Request) if the limitacionAmbiente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/limitacion-ambientes")
    public ResponseEntity<LimitacionAmbiente> createLimitacionAmbiente(@Valid @RequestBody LimitacionAmbiente limitacionAmbiente) throws URISyntaxException {
        log.debug("REST request to save LimitacionAmbiente : {}", limitacionAmbiente);
        if (limitacionAmbiente.getId() != null) {
            throw new BadRequestAlertException("A new limitacionAmbiente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LimitacionAmbiente result = limitacionAmbienteRepository.save(limitacionAmbiente);
        return ResponseEntity.created(new URI("/api/limitacion-ambientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /limitacion-ambientes : Updates an existing limitacionAmbiente.
     *
     * @param limitacionAmbiente the limitacionAmbiente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated limitacionAmbiente,
     * or with status 400 (Bad Request) if the limitacionAmbiente is not valid,
     * or with status 500 (Internal Server Error) if the limitacionAmbiente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/limitacion-ambientes")
    public ResponseEntity<LimitacionAmbiente> updateLimitacionAmbiente(@Valid @RequestBody LimitacionAmbiente limitacionAmbiente) throws URISyntaxException {
        log.debug("REST request to update LimitacionAmbiente : {}", limitacionAmbiente);
        if (limitacionAmbiente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LimitacionAmbiente result = limitacionAmbienteRepository.save(limitacionAmbiente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, limitacionAmbiente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /limitacion-ambientes : get all the limitacionAmbientes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of limitacionAmbientes in body
     */
    @GetMapping("/limitacion-ambientes")
    public List<LimitacionAmbiente> getAllLimitacionAmbientes() {
        log.debug("REST request to get all LimitacionAmbientes");
        return limitacionAmbienteRepository.findAll();
    }

    /**
     * GET  /limitacion-ambientes/:id : get the "id" limitacionAmbiente.
     *
     * @param id the id of the limitacionAmbiente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the limitacionAmbiente, or with status 404 (Not Found)
     */
    @GetMapping("/limitacion-ambientes/{id}")
    public ResponseEntity<LimitacionAmbiente> getLimitacionAmbiente(@PathVariable Long id) {
        log.debug("REST request to get LimitacionAmbiente : {}", id);
        Optional<LimitacionAmbiente> limitacionAmbiente = limitacionAmbienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(limitacionAmbiente);
    }

    /**
     * DELETE  /limitacion-ambientes/:id : delete the "id" limitacionAmbiente.
     *
     * @param id the id of the limitacionAmbiente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/limitacion-ambientes/{id}")
    public ResponseEntity<Void> deleteLimitacionAmbiente(@PathVariable Long id) {
        log.debug("REST request to delete LimitacionAmbiente : {}", id);
        limitacionAmbienteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
