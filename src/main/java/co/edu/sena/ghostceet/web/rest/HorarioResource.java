package co.edu.sena.ghostceet.web.rest;
import co.edu.sena.ghostceet.domain.Horario;
import co.edu.sena.ghostceet.repository.HorarioRepository;
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
 * REST controller for managing Horario.
 */
@RestController
@RequestMapping("/api")
public class HorarioResource {

    private final Logger log = LoggerFactory.getLogger(HorarioResource.class);

    private static final String ENTITY_NAME = "horario";

    private final HorarioRepository horarioRepository;

    public HorarioResource(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    /**
     * POST  /horarios : Create a new horario.
     *
     * @param horario the horario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new horario, or with status 400 (Bad Request) if the horario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/horarios")
    public ResponseEntity<Horario> createHorario(@Valid @RequestBody Horario horario) throws URISyntaxException {
        log.debug("REST request to save Horario : {}", horario);
        if (horario.getId() != null) {
            throw new BadRequestAlertException("A new horario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Horario result = horarioRepository.save(horario);
        return ResponseEntity.created(new URI("/api/horarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /horarios : Updates an existing horario.
     *
     * @param horario the horario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated horario,
     * or with status 400 (Bad Request) if the horario is not valid,
     * or with status 500 (Internal Server Error) if the horario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/horarios")
    public ResponseEntity<Horario> updateHorario(@Valid @RequestBody Horario horario) throws URISyntaxException {
        log.debug("REST request to update Horario : {}", horario);
        if (horario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Horario result = horarioRepository.save(horario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, horario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /horarios : get all the horarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of horarios in body
     */
    @GetMapping("/horarios")
    public List<Horario> getAllHorarios() {
        log.debug("REST request to get all Horarios");
        return horarioRepository.findAll();
    }

    /**
     * GET  /horarios/:id : get the "id" horario.
     *
     * @param id the id of the horario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the horario, or with status 404 (Not Found)
     */
    @GetMapping("/horarios/{id}")
    public ResponseEntity<Horario> getHorario(@PathVariable Long id) {
        log.debug("REST request to get Horario : {}", id);
        Optional<Horario> horario = horarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(horario);
    }

    /**
     * DELETE  /horarios/:id : delete the "id" horario.
     *
     * @param id the id of the horario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/horarios/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        log.debug("REST request to delete Horario : {}", id);
        horarioRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
