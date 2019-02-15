package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Dia;
import co.edu.sena.ghostceet.repository.DiaRepository;
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
 * REST controller for managing Dia.
 */
@RestController
@RequestMapping("/api")
public class DiaResource {

    private final Logger log = LoggerFactory.getLogger(DiaResource.class);

    private static final String ENTITY_NAME = "dia";

    private final DiaRepository diaRepository;

    public DiaResource(DiaRepository diaRepository) {
        this.diaRepository = diaRepository;
    }

    /**
     * POST  /dias : Create a new dia.
     *
     * @param dia the dia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dia, or with status 400 (Bad Request) if the dia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dias")
    public ResponseEntity<Dia> createDia(@Valid @RequestBody Dia dia) throws URISyntaxException {
        log.debug("REST request to save Dia : {}", dia);
        if (dia.getId() != null) {
            throw new BadRequestAlertException("A new dia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dia result = diaRepository.save(dia);
        return ResponseEntity.created(new URI("/api/dias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dias : Updates an existing dia.
     *
     * @param dia the dia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dia,
     * or with status 400 (Bad Request) if the dia is not valid,
     * or with status 500 (Internal Server Error) if the dia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dias")
    public ResponseEntity<Dia> updateDia(@Valid @RequestBody Dia dia) throws URISyntaxException {
        log.debug("REST request to update Dia : {}", dia);
        if (dia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dia result = diaRepository.save(dia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dias : get all the dias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dias in body
     */
    @GetMapping("/dias")
    public List<Dia> getAllDias() {
        log.debug("REST request to get all Dias");
        return diaRepository.findAll();
    }

    /**
     * GET  /dias/:id : get the "id" dia.
     *
     * @param id the id of the dia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dia, or with status 404 (Not Found)
     */
    @GetMapping("/dias/{id}")
    public ResponseEntity<Dia> getDia(@PathVariable Long id) {
        log.debug("REST request to get Dia : {}", id);
        Optional<Dia> dia = diaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dia);
    }

    /**
     * DELETE  /dias/:id : delete the "id" dia.
     *
     * @param id the id of the dia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dias/{id}")
    public ResponseEntity<Void> deleteDia(@PathVariable Long id) {
        log.debug("REST request to delete Dia : {}", id);
        diaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
