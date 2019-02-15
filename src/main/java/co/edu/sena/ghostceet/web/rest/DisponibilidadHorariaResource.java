package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.DisponibilidadHoraria;
import co.edu.sena.ghostceet.repository.DisponibilidadHorariaRepository;
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
 * REST controller for managing DisponibilidadHoraria.
 */
@RestController
@RequestMapping("/api")
public class DisponibilidadHorariaResource {

    private final Logger log = LoggerFactory.getLogger(DisponibilidadHorariaResource.class);

    private static final String ENTITY_NAME = "disponibilidadHoraria";

    private final DisponibilidadHorariaRepository disponibilidadHorariaRepository;

    public DisponibilidadHorariaResource(DisponibilidadHorariaRepository disponibilidadHorariaRepository) {
        this.disponibilidadHorariaRepository = disponibilidadHorariaRepository;
    }

    /**
     * POST  /disponibilidad-horarias : Create a new disponibilidadHoraria.
     *
     * @param disponibilidadHoraria the disponibilidadHoraria to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disponibilidadHoraria, or with status 400 (Bad Request) if the disponibilidadHoraria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disponibilidad-horarias")
    public ResponseEntity<DisponibilidadHoraria> createDisponibilidadHoraria(@Valid @RequestBody DisponibilidadHoraria disponibilidadHoraria) throws URISyntaxException {
        log.debug("REST request to save DisponibilidadHoraria : {}", disponibilidadHoraria);
        if (disponibilidadHoraria.getId() != null) {
            throw new BadRequestAlertException("A new disponibilidadHoraria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisponibilidadHoraria result = disponibilidadHorariaRepository.save(disponibilidadHoraria);
        return ResponseEntity.created(new URI("/api/disponibilidad-horarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disponibilidad-horarias : Updates an existing disponibilidadHoraria.
     *
     * @param disponibilidadHoraria the disponibilidadHoraria to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disponibilidadHoraria,
     * or with status 400 (Bad Request) if the disponibilidadHoraria is not valid,
     * or with status 500 (Internal Server Error) if the disponibilidadHoraria couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disponibilidad-horarias")
    public ResponseEntity<DisponibilidadHoraria> updateDisponibilidadHoraria(@Valid @RequestBody DisponibilidadHoraria disponibilidadHoraria) throws URISyntaxException {
        log.debug("REST request to update DisponibilidadHoraria : {}", disponibilidadHoraria);
        if (disponibilidadHoraria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisponibilidadHoraria result = disponibilidadHorariaRepository.save(disponibilidadHoraria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disponibilidadHoraria.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disponibilidad-horarias : get all the disponibilidadHorarias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of disponibilidadHorarias in body
     */
    @GetMapping("/disponibilidad-horarias")
    public List<DisponibilidadHoraria> getAllDisponibilidadHorarias() {
        log.debug("REST request to get all DisponibilidadHorarias");
        return disponibilidadHorariaRepository.findAll();
    }

    /**
     * GET  /disponibilidad-horarias/:id : get the "id" disponibilidadHoraria.
     *
     * @param id the id of the disponibilidadHoraria to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disponibilidadHoraria, or with status 404 (Not Found)
     */
    @GetMapping("/disponibilidad-horarias/{id}")
    public ResponseEntity<DisponibilidadHoraria> getDisponibilidadHoraria(@PathVariable Long id) {
        log.debug("REST request to get DisponibilidadHoraria : {}", id);
        Optional<DisponibilidadHoraria> disponibilidadHoraria = disponibilidadHorariaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disponibilidadHoraria);
    }

    /**
     * DELETE  /disponibilidad-horarias/:id : delete the "id" disponibilidadHoraria.
     *
     * @param id the id of the disponibilidadHoraria to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disponibilidad-horarias/{id}")
    public ResponseEntity<Void> deleteDisponibilidadHoraria(@PathVariable Long id) {
        log.debug("REST request to delete DisponibilidadHoraria : {}", id);
        disponibilidadHorariaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
