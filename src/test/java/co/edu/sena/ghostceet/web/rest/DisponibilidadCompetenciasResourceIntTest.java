package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.DisponibilidadCompetencias;
import co.edu.sena.ghostceet.domain.Instructor;
import co.edu.sena.ghostceet.domain.VinculacionInstructor;
import co.edu.sena.ghostceet.repository.DisponibilidadCompetenciasRepository;
import co.edu.sena.ghostceet.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static co.edu.sena.ghostceet.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DisponibilidadCompetenciasResource REST controller.
 *
 * @see DisponibilidadCompetenciasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class DisponibilidadCompetenciasResourceIntTest {

    @Autowired
    private DisponibilidadCompetenciasRepository disponibilidadCompetenciasRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDisponibilidadCompetenciasMockMvc;

    private DisponibilidadCompetencias disponibilidadCompetencias;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisponibilidadCompetenciasResource disponibilidadCompetenciasResource = new DisponibilidadCompetenciasResource(disponibilidadCompetenciasRepository);
        this.restDisponibilidadCompetenciasMockMvc = MockMvcBuilders.standaloneSetup(disponibilidadCompetenciasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisponibilidadCompetencias createEntity(EntityManager em) {
        DisponibilidadCompetencias disponibilidadCompetencias = new DisponibilidadCompetencias();
        // Add required entity
        Instructor instructor = InstructorResourceIntTest.createEntity(em);
        em.persist(instructor);
        em.flush();
        disponibilidadCompetencias.setInstructor(instructor);
        // Add required entity
        VinculacionInstructor vinculacionInstructor = VinculacionInstructorResourceIntTest.createEntity(em);
        em.persist(vinculacionInstructor);
        em.flush();
        disponibilidadCompetencias.setVinculacionInstructor(vinculacionInstructor);
        return disponibilidadCompetencias;
    }

    @Before
    public void initTest() {
        disponibilidadCompetencias = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisponibilidadCompetencias() throws Exception {
        int databaseSizeBeforeCreate = disponibilidadCompetenciasRepository.findAll().size();

        // Create the DisponibilidadCompetencias
        restDisponibilidadCompetenciasMockMvc.perform(post("/api/disponibilidad-competencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disponibilidadCompetencias)))
            .andExpect(status().isCreated());

        // Validate the DisponibilidadCompetencias in the database
        List<DisponibilidadCompetencias> disponibilidadCompetenciasList = disponibilidadCompetenciasRepository.findAll();
        assertThat(disponibilidadCompetenciasList).hasSize(databaseSizeBeforeCreate + 1);
        DisponibilidadCompetencias testDisponibilidadCompetencias = disponibilidadCompetenciasList.get(disponibilidadCompetenciasList.size() - 1);
    }

    @Test
    @Transactional
    public void createDisponibilidadCompetenciasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disponibilidadCompetenciasRepository.findAll().size();

        // Create the DisponibilidadCompetencias with an existing ID
        disponibilidadCompetencias.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisponibilidadCompetenciasMockMvc.perform(post("/api/disponibilidad-competencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disponibilidadCompetencias)))
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadCompetencias in the database
        List<DisponibilidadCompetencias> disponibilidadCompetenciasList = disponibilidadCompetenciasRepository.findAll();
        assertThat(disponibilidadCompetenciasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDisponibilidadCompetencias() throws Exception {
        // Initialize the database
        disponibilidadCompetenciasRepository.saveAndFlush(disponibilidadCompetencias);

        // Get all the disponibilidadCompetenciasList
        restDisponibilidadCompetenciasMockMvc.perform(get("/api/disponibilidad-competencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disponibilidadCompetencias.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDisponibilidadCompetencias() throws Exception {
        // Initialize the database
        disponibilidadCompetenciasRepository.saveAndFlush(disponibilidadCompetencias);

        // Get the disponibilidadCompetencias
        restDisponibilidadCompetenciasMockMvc.perform(get("/api/disponibilidad-competencias/{id}", disponibilidadCompetencias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disponibilidadCompetencias.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDisponibilidadCompetencias() throws Exception {
        // Get the disponibilidadCompetencias
        restDisponibilidadCompetenciasMockMvc.perform(get("/api/disponibilidad-competencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisponibilidadCompetencias() throws Exception {
        // Initialize the database
        disponibilidadCompetenciasRepository.saveAndFlush(disponibilidadCompetencias);

        int databaseSizeBeforeUpdate = disponibilidadCompetenciasRepository.findAll().size();

        // Update the disponibilidadCompetencias
        DisponibilidadCompetencias updatedDisponibilidadCompetencias = disponibilidadCompetenciasRepository.findById(disponibilidadCompetencias.getId()).get();
        // Disconnect from session so that the updates on updatedDisponibilidadCompetencias are not directly saved in db
        em.detach(updatedDisponibilidadCompetencias);

        restDisponibilidadCompetenciasMockMvc.perform(put("/api/disponibilidad-competencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisponibilidadCompetencias)))
            .andExpect(status().isOk());

        // Validate the DisponibilidadCompetencias in the database
        List<DisponibilidadCompetencias> disponibilidadCompetenciasList = disponibilidadCompetenciasRepository.findAll();
        assertThat(disponibilidadCompetenciasList).hasSize(databaseSizeBeforeUpdate);
        DisponibilidadCompetencias testDisponibilidadCompetencias = disponibilidadCompetenciasList.get(disponibilidadCompetenciasList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDisponibilidadCompetencias() throws Exception {
        int databaseSizeBeforeUpdate = disponibilidadCompetenciasRepository.findAll().size();

        // Create the DisponibilidadCompetencias

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisponibilidadCompetenciasMockMvc.perform(put("/api/disponibilidad-competencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disponibilidadCompetencias)))
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadCompetencias in the database
        List<DisponibilidadCompetencias> disponibilidadCompetenciasList = disponibilidadCompetenciasRepository.findAll();
        assertThat(disponibilidadCompetenciasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisponibilidadCompetencias() throws Exception {
        // Initialize the database
        disponibilidadCompetenciasRepository.saveAndFlush(disponibilidadCompetencias);

        int databaseSizeBeforeDelete = disponibilidadCompetenciasRepository.findAll().size();

        // Delete the disponibilidadCompetencias
        restDisponibilidadCompetenciasMockMvc.perform(delete("/api/disponibilidad-competencias/{id}", disponibilidadCompetencias.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DisponibilidadCompetencias> disponibilidadCompetenciasList = disponibilidadCompetenciasRepository.findAll();
        assertThat(disponibilidadCompetenciasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisponibilidadCompetencias.class);
        DisponibilidadCompetencias disponibilidadCompetencias1 = new DisponibilidadCompetencias();
        disponibilidadCompetencias1.setId(1L);
        DisponibilidadCompetencias disponibilidadCompetencias2 = new DisponibilidadCompetencias();
        disponibilidadCompetencias2.setId(disponibilidadCompetencias1.getId());
        assertThat(disponibilidadCompetencias1).isEqualTo(disponibilidadCompetencias2);
        disponibilidadCompetencias2.setId(2L);
        assertThat(disponibilidadCompetencias1).isNotEqualTo(disponibilidadCompetencias2);
        disponibilidadCompetencias1.setId(null);
        assertThat(disponibilidadCompetencias1).isNotEqualTo(disponibilidadCompetencias2);
    }
}
