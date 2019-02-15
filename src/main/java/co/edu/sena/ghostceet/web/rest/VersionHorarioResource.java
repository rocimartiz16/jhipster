package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.VersionHorario;
import co.edu.sena.ghostceet.repository.VersionHorarioRepository;
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
 * REST controller for managing VersionHorario.
 */
@RestController
@RequestMapping("/api")
public class VersionHorarioResource {

    private final Logger log = LoggerFactory.getLogger(VersionHorarioResource.class);

    private static final String ENTITY_NAME = "versionHorario";

    private final VersionHorarioRepository versionHorarioRepository;

    public VersionHorarioResource(VersionHorarioRepository versionHorarioRepository) {
        this.versionHorarioRepository = versionHorarioRepository;
    }

    /**
     * POST  /version-horarios : Create a new versionHorario.
     *
     * @param versionHorario the versionHorario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new versionHorario, or with status 400 (Bad Request) if the versionHorario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/version-horarios")
    public ResponseEntity<VersionHorario> createVersionHorario(@Valid @RequestBody VersionHorario versionHorario) throws URISyntaxException {
        log.debug("REST request to save VersionHorario : {}", versionHorario);
        if (versionHorario.getId() != null) {
            throw new BadRequestAlertException("A new versionHorario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VersionHorario result = versionHorarioRepository.save(versionHorario);
        return ResponseEntity.created(new URI("/api/version-horarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /version-horarios : Updates an existing versionHorario.
     *
     * @param versionHorario the versionHorario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated versionHorario,
     * or with status 400 (Bad Request) if the versionHorario is not valid,
     * or with status 500 (Internal Server Error) if the versionHorario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/version-horarios")
    public ResponseEntity<VersionHorario> updateVersionHorario(@Valid @RequestBody VersionHorario versionHorario) throws URISyntaxException {
        log.debug("REST request to update VersionHorario : {}", versionHorario);
        if (versionHorario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VersionHorario result = versionHorarioRepository.save(versionHorario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, versionHorario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /version-horarios : get all the versionHorarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of versionHorarios in body
     */
    @GetMapping("/version-horarios")
    public ResponseEntity<List<VersionHorario>> getAllVersionHorarios(Pageable pageable) {
        log.debug("REST request to get a page of VersionHorarios");
        Page<VersionHorario> page = versionHorarioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/version-horarios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /version-horarios/:id : get the "id" versionHorario.
     *
     * @param id the id of the versionHorario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the versionHorario, or with status 404 (Not Found)
     */
    @GetMapping("/version-horarios/{id}")
    public ResponseEntity<VersionHorario> getVersionHorario(@PathVariable Long id) {
        log.debug("REST request to get VersionHorario : {}", id);
        Optional<VersionHorario> versionHorario = versionHorarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(versionHorario);
    }

    /**
     * DELETE  /version-horarios/:id : delete the "id" versionHorario.
     *
     * @param id the id of the versionHorario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/version-horarios/{id}")
    public ResponseEntity<Void> deleteVersionHorario(@PathVariable Long id) {
        log.debug("REST request to delete VersionHorario : {}", id);
        versionHorarioRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
