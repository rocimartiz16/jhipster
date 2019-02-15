package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Vinculacion;
import co.edu.sena.ghostceet.repository.VinculacionRepository;
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
 * REST controller for managing Vinculacion.
 */
@RestController
@RequestMapping("/api")
public class VinculacionResource {

    private final Logger log = LoggerFactory.getLogger(VinculacionResource.class);

    private static final String ENTITY_NAME = "vinculacion";

    private final VinculacionRepository vinculacionRepository;

    public VinculacionResource(VinculacionRepository vinculacionRepository) {
        this.vinculacionRepository = vinculacionRepository;
    }

    /**
     * POST  /vinculacions : Create a new vinculacion.
     *
     * @param vinculacion the vinculacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vinculacion, or with status 400 (Bad Request) if the vinculacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vinculacions")
    public ResponseEntity<Vinculacion> createVinculacion(@Valid @RequestBody Vinculacion vinculacion) throws URISyntaxException {
        log.debug("REST request to save Vinculacion : {}", vinculacion);
        if (vinculacion.getId() != null) {
            throw new BadRequestAlertException("A new vinculacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vinculacion result = vinculacionRepository.save(vinculacion);
        return ResponseEntity.created(new URI("/api/vinculacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vinculacions : Updates an existing vinculacion.
     *
     * @param vinculacion the vinculacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vinculacion,
     * or with status 400 (Bad Request) if the vinculacion is not valid,
     * or with status 500 (Internal Server Error) if the vinculacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vinculacions")
    public ResponseEntity<Vinculacion> updateVinculacion(@Valid @RequestBody Vinculacion vinculacion) throws URISyntaxException {
        log.debug("REST request to update Vinculacion : {}", vinculacion);
        if (vinculacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vinculacion result = vinculacionRepository.save(vinculacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vinculacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vinculacions : get all the vinculacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vinculacions in body
     */
    @GetMapping("/vinculacions")
    public ResponseEntity<List<Vinculacion>> getAllVinculacions(Pageable pageable) {
        log.debug("REST request to get a page of Vinculacions");
        Page<Vinculacion> page = vinculacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vinculacions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /vinculacions/:id : get the "id" vinculacion.
     *
     * @param id the id of the vinculacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vinculacion, or with status 404 (Not Found)
     */
    @GetMapping("/vinculacions/{id}")
    public ResponseEntity<Vinculacion> getVinculacion(@PathVariable Long id) {
        log.debug("REST request to get Vinculacion : {}", id);
        Optional<Vinculacion> vinculacion = vinculacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vinculacion);
    }

    /**
     * DELETE  /vinculacions/:id : delete the "id" vinculacion.
     *
     * @param id the id of the vinculacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vinculacions/{id}")
    public ResponseEntity<Void> deleteVinculacion(@PathVariable Long id) {
        log.debug("REST request to delete Vinculacion : {}", id);
        vinculacionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
