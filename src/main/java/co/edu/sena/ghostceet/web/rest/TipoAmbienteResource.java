package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.TipoAmbiente;
import co.edu.sena.ghostceet.repository.TipoAmbienteRepository;
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
 * REST controller for managing TipoAmbiente.
 */
@RestController
@RequestMapping("/api")
public class TipoAmbienteResource {

    private final Logger log = LoggerFactory.getLogger(TipoAmbienteResource.class);

    private static final String ENTITY_NAME = "tipoAmbiente";

    private final TipoAmbienteRepository tipoAmbienteRepository;

    public TipoAmbienteResource(TipoAmbienteRepository tipoAmbienteRepository) {
        this.tipoAmbienteRepository = tipoAmbienteRepository;
    }

    /**
     * POST  /tipo-ambientes : Create a new tipoAmbiente.
     *
     * @param tipoAmbiente the tipoAmbiente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoAmbiente, or with status 400 (Bad Request) if the tipoAmbiente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-ambientes")
    public ResponseEntity<TipoAmbiente> createTipoAmbiente(@Valid @RequestBody TipoAmbiente tipoAmbiente) throws URISyntaxException {
        log.debug("REST request to save TipoAmbiente : {}", tipoAmbiente);
        if (tipoAmbiente.getId() != null) {
            throw new BadRequestAlertException("A new tipoAmbiente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoAmbiente result = tipoAmbienteRepository.save(tipoAmbiente);
        return ResponseEntity.created(new URI("/api/tipo-ambientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-ambientes : Updates an existing tipoAmbiente.
     *
     * @param tipoAmbiente the tipoAmbiente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoAmbiente,
     * or with status 400 (Bad Request) if the tipoAmbiente is not valid,
     * or with status 500 (Internal Server Error) if the tipoAmbiente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-ambientes")
    public ResponseEntity<TipoAmbiente> updateTipoAmbiente(@Valid @RequestBody TipoAmbiente tipoAmbiente) throws URISyntaxException {
        log.debug("REST request to update TipoAmbiente : {}", tipoAmbiente);
        if (tipoAmbiente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoAmbiente result = tipoAmbienteRepository.save(tipoAmbiente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoAmbiente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-ambientes : get all the tipoAmbientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoAmbientes in body
     */
    @GetMapping("/tipo-ambientes")
    public ResponseEntity<List<TipoAmbiente>> getAllTipoAmbientes(Pageable pageable) {
        log.debug("REST request to get a page of TipoAmbientes");
        Page<TipoAmbiente> page = tipoAmbienteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-ambientes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tipo-ambientes/:id : get the "id" tipoAmbiente.
     *
     * @param id the id of the tipoAmbiente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoAmbiente, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-ambientes/{id}")
    public ResponseEntity<TipoAmbiente> getTipoAmbiente(@PathVariable Long id) {
        log.debug("REST request to get TipoAmbiente : {}", id);
        Optional<TipoAmbiente> tipoAmbiente = tipoAmbienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoAmbiente);
    }

    /**
     * DELETE  /tipo-ambientes/:id : delete the "id" tipoAmbiente.
     *
     * @param id the id of the tipoAmbiente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-ambientes/{id}")
    public ResponseEntity<Void> deleteTipoAmbiente(@PathVariable Long id) {
        log.debug("REST request to delete TipoAmbiente : {}", id);
        tipoAmbienteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
