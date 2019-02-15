package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.DiaJornada;
import co.edu.sena.ghostceet.repository.DiaJornadaRepository;
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
 * REST controller for managing DiaJornada.
 */
@RestController
@RequestMapping("/api")
public class DiaJornadaResource {

    private final Logger log = LoggerFactory.getLogger(DiaJornadaResource.class);

    private static final String ENTITY_NAME = "diaJornada";

    private final DiaJornadaRepository diaJornadaRepository;

    public DiaJornadaResource(DiaJornadaRepository diaJornadaRepository) {
        this.diaJornadaRepository = diaJornadaRepository;
    }

    /**
     * POST  /dia-jornadas : Create a new diaJornada.
     *
     * @param diaJornada the diaJornada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diaJornada, or with status 400 (Bad Request) if the diaJornada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dia-jornadas")
    public ResponseEntity<DiaJornada> createDiaJornada(@Valid @RequestBody DiaJornada diaJornada) throws URISyntaxException {
        log.debug("REST request to save DiaJornada : {}", diaJornada);
        if (diaJornada.getId() != null) {
            throw new BadRequestAlertException("A new diaJornada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiaJornada result = diaJornadaRepository.save(diaJornada);
        return ResponseEntity.created(new URI("/api/dia-jornadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dia-jornadas : Updates an existing diaJornada.
     *
     * @param diaJornada the diaJornada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diaJornada,
     * or with status 400 (Bad Request) if the diaJornada is not valid,
     * or with status 500 (Internal Server Error) if the diaJornada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dia-jornadas")
    public ResponseEntity<DiaJornada> updateDiaJornada(@Valid @RequestBody DiaJornada diaJornada) throws URISyntaxException {
        log.debug("REST request to update DiaJornada : {}", diaJornada);
        if (diaJornada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiaJornada result = diaJornadaRepository.save(diaJornada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diaJornada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dia-jornadas : get all the diaJornadas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of diaJornadas in body
     */
    @GetMapping("/dia-jornadas")
    public ResponseEntity<List<DiaJornada>> getAllDiaJornadas(Pageable pageable) {
        log.debug("REST request to get a page of DiaJornadas");
        Page<DiaJornada> page = diaJornadaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dia-jornadas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dia-jornadas/:id : get the "id" diaJornada.
     *
     * @param id the id of the diaJornada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diaJornada, or with status 404 (Not Found)
     */
    @GetMapping("/dia-jornadas/{id}")
    public ResponseEntity<DiaJornada> getDiaJornada(@PathVariable Long id) {
        log.debug("REST request to get DiaJornada : {}", id);
        Optional<DiaJornada> diaJornada = diaJornadaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diaJornada);
    }

    /**
     * DELETE  /dia-jornadas/:id : delete the "id" diaJornada.
     *
     * @param id the id of the diaJornada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dia-jornadas/{id}")
    public ResponseEntity<Void> deleteDiaJornada(@PathVariable Long id) {
        log.debug("REST request to delete DiaJornada : {}", id);
        diaJornadaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
