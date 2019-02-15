package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.DisponibilidadCompetencias;
import co.edu.sena.ghostceet.repository.DisponibilidadCompetenciasRepository;
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
 * REST controller for managing DisponibilidadCompetencias.
 */
@RestController
@RequestMapping("/api")
public class DisponibilidadCompetenciasResource {

    private final Logger log = LoggerFactory.getLogger(DisponibilidadCompetenciasResource.class);

    private static final String ENTITY_NAME = "disponibilidadCompetencias";

    private final DisponibilidadCompetenciasRepository disponibilidadCompetenciasRepository;

    public DisponibilidadCompetenciasResource(DisponibilidadCompetenciasRepository disponibilidadCompetenciasRepository) {
        this.disponibilidadCompetenciasRepository = disponibilidadCompetenciasRepository;
    }

    /**
     * POST  /disponibilidad-competencias : Create a new disponibilidadCompetencias.
     *
     * @param disponibilidadCompetencias the disponibilidadCompetencias to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disponibilidadCompetencias, or with status 400 (Bad Request) if the disponibilidadCompetencias has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disponibilidad-competencias")
    public ResponseEntity<DisponibilidadCompetencias> createDisponibilidadCompetencias(@Valid @RequestBody DisponibilidadCompetencias disponibilidadCompetencias) throws URISyntaxException {
        log.debug("REST request to save DisponibilidadCompetencias : {}", disponibilidadCompetencias);
        if (disponibilidadCompetencias.getId() != null) {
            throw new BadRequestAlertException("A new disponibilidadCompetencias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisponibilidadCompetencias result = disponibilidadCompetenciasRepository.save(disponibilidadCompetencias);
        return ResponseEntity.created(new URI("/api/disponibilidad-competencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disponibilidad-competencias : Updates an existing disponibilidadCompetencias.
     *
     * @param disponibilidadCompetencias the disponibilidadCompetencias to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disponibilidadCompetencias,
     * or with status 400 (Bad Request) if the disponibilidadCompetencias is not valid,
     * or with status 500 (Internal Server Error) if the disponibilidadCompetencias couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disponibilidad-competencias")
    public ResponseEntity<DisponibilidadCompetencias> updateDisponibilidadCompetencias(@Valid @RequestBody DisponibilidadCompetencias disponibilidadCompetencias) throws URISyntaxException {
        log.debug("REST request to update DisponibilidadCompetencias : {}", disponibilidadCompetencias);
        if (disponibilidadCompetencias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisponibilidadCompetencias result = disponibilidadCompetenciasRepository.save(disponibilidadCompetencias);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disponibilidadCompetencias.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disponibilidad-competencias : get all the disponibilidadCompetencias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of disponibilidadCompetencias in body
     */
    @GetMapping("/disponibilidad-competencias")
    public ResponseEntity<List<DisponibilidadCompetencias>> getAllDisponibilidadCompetencias(Pageable pageable) {
        log.debug("REST request to get a page of DisponibilidadCompetencias");
        Page<DisponibilidadCompetencias> page = disponibilidadCompetenciasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/disponibilidad-competencias");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /disponibilidad-competencias/:id : get the "id" disponibilidadCompetencias.
     *
     * @param id the id of the disponibilidadCompetencias to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disponibilidadCompetencias, or with status 404 (Not Found)
     */
    @GetMapping("/disponibilidad-competencias/{id}")
    public ResponseEntity<DisponibilidadCompetencias> getDisponibilidadCompetencias(@PathVariable Long id) {
        log.debug("REST request to get DisponibilidadCompetencias : {}", id);
        Optional<DisponibilidadCompetencias> disponibilidadCompetencias = disponibilidadCompetenciasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disponibilidadCompetencias);
    }

    /**
     * DELETE  /disponibilidad-competencias/:id : delete the "id" disponibilidadCompetencias.
     *
     * @param id the id of the disponibilidadCompetencias to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disponibilidad-competencias/{id}")
    public ResponseEntity<Void> deleteDisponibilidadCompetencias(@PathVariable Long id) {
        log.debug("REST request to delete DisponibilidadCompetencias : {}", id);
        disponibilidadCompetenciasRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
