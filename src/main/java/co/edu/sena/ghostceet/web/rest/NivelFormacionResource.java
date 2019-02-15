package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.NivelFormacion;
import co.edu.sena.ghostceet.repository.NivelFormacionRepository;
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
 * REST controller for managing NivelFormacion.
 */
@RestController
@RequestMapping("/api")
public class NivelFormacionResource {

    private final Logger log = LoggerFactory.getLogger(NivelFormacionResource.class);

    private static final String ENTITY_NAME = "nivelFormacion";

    private final NivelFormacionRepository nivelFormacionRepository;

    public NivelFormacionResource(NivelFormacionRepository nivelFormacionRepository) {
        this.nivelFormacionRepository = nivelFormacionRepository;
    }

    /**
     * POST  /nivel-formacions : Create a new nivelFormacion.
     *
     * @param nivelFormacion the nivelFormacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nivelFormacion, or with status 400 (Bad Request) if the nivelFormacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nivel-formacions")
    public ResponseEntity<NivelFormacion> createNivelFormacion(@Valid @RequestBody NivelFormacion nivelFormacion) throws URISyntaxException {
        log.debug("REST request to save NivelFormacion : {}", nivelFormacion);
        if (nivelFormacion.getId() != null) {
            throw new BadRequestAlertException("A new nivelFormacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NivelFormacion result = nivelFormacionRepository.save(nivelFormacion);
        return ResponseEntity.created(new URI("/api/nivel-formacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nivel-formacions : Updates an existing nivelFormacion.
     *
     * @param nivelFormacion the nivelFormacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nivelFormacion,
     * or with status 400 (Bad Request) if the nivelFormacion is not valid,
     * or with status 500 (Internal Server Error) if the nivelFormacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nivel-formacions")
    public ResponseEntity<NivelFormacion> updateNivelFormacion(@Valid @RequestBody NivelFormacion nivelFormacion) throws URISyntaxException {
        log.debug("REST request to update NivelFormacion : {}", nivelFormacion);
        if (nivelFormacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NivelFormacion result = nivelFormacionRepository.save(nivelFormacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nivelFormacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nivel-formacions : get all the nivelFormacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nivelFormacions in body
     */
    @GetMapping("/nivel-formacions")
    public ResponseEntity<List<NivelFormacion>> getAllNivelFormacions(Pageable pageable) {
        log.debug("REST request to get a page of NivelFormacions");
        Page<NivelFormacion> page = nivelFormacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nivel-formacions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nivel-formacions/:id : get the "id" nivelFormacion.
     *
     * @param id the id of the nivelFormacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nivelFormacion, or with status 404 (Not Found)
     */
    @GetMapping("/nivel-formacions/{id}")
    public ResponseEntity<NivelFormacion> getNivelFormacion(@PathVariable Long id) {
        log.debug("REST request to get NivelFormacion : {}", id);
        Optional<NivelFormacion> nivelFormacion = nivelFormacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nivelFormacion);
    }

    /**
     * DELETE  /nivel-formacions/:id : delete the "id" nivelFormacion.
     *
     * @param id the id of the nivelFormacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nivel-formacions/{id}")
    public ResponseEntity<Void> deleteNivelFormacion(@PathVariable Long id) {
        log.debug("REST request to delete NivelFormacion : {}", id);
        nivelFormacionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
