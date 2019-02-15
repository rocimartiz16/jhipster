package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.DisponibilidadHoraria;
import co.edu.sena.ghostceet.domain.VinculacionInstructor;
import co.edu.sena.ghostceet.domain.JornadaInstructor;
import co.edu.sena.ghostceet.domain.Instructor;
import co.edu.sena.ghostceet.repository.DisponibilidadHorariaRepository;
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
 * Test class for the DisponibilidadHorariaResource REST controller.
 *
 * @see DisponibilidadHorariaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class DisponibilidadHorariaResourceIntTest {

    @Autowired
    private DisponibilidadHorariaRepository disponibilidadHorariaRepository;

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

    private MockMvc restDisponibilidadHorariaMockMvc;

    private DisponibilidadHoraria disponibilidadHoraria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisponibilidadHorariaResource disponibilidadHorariaResource = new DisponibilidadHorariaResource(disponibilidadHorariaRepository);
        this.restDisponibilidadHorariaMockMvc = MockMvcBuilders.standaloneSetup(disponibilidadHorariaResource)
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
    public static DisponibilidadHoraria createEntity(EntityManager em) {
        DisponibilidadHoraria disponibilidadHoraria = new DisponibilidadHoraria();
        // Add required entity
        VinculacionInstructor vinculacionInstructor = VinculacionInstructorResourceIntTest.createEntity(em);
        em.persist(vinculacionInstructor);
        em.flush();
        disponibilidadHoraria.setVinculacionInstructor(vinculacionInstructor);
        // Add required entity
        JornadaInstructor jornadaInstructor = JornadaInstructorResourceIntTest.createEntity(em);
        em.persist(jornadaInstructor);
        em.flush();
        disponibilidadHoraria.setJornada(jornadaInstructor);
        // Add required entity
        Instructor instructor = InstructorResourceIntTest.createEntity(em);
        em.persist(instructor);
        em.flush();
        disponibilidadHoraria.setInstructor(instructor);
        return disponibilidadHoraria;
    }

    @Before
    public void initTest() {
        disponibilidadHoraria = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisponibilidadHoraria() throws Exception {
        int databaseSizeBeforeCreate = disponibilidadHorariaRepository.findAll().size();

        // Create the DisponibilidadHoraria
        restDisponibilidadHorariaMockMvc.perform(post("/api/disponibilidad-horarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disponibilidadHoraria)))
            .andExpect(status().isCreated());

        // Validate the DisponibilidadHoraria in the database
        List<DisponibilidadHoraria> disponibilidadHorariaList = disponibilidadHorariaRepository.findAll();
        assertThat(disponibilidadHorariaList).hasSize(databaseSizeBeforeCreate + 1);
        DisponibilidadHoraria testDisponibilidadHoraria = disponibilidadHorariaList.get(disponibilidadHorariaList.size() - 1);
    }

    @Test
    @Transactional
    public void createDisponibilidadHorariaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disponibilidadHorariaRepository.findAll().size();

        // Create the DisponibilidadHoraria with an existing ID
        disponibilidadHoraria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisponibilidadHorariaMockMvc.perform(post("/api/disponibilidad-horarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disponibilidadHoraria)))
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadHoraria in the database
        List<DisponibilidadHoraria> disponibilidadHorariaList = disponibilidadHorariaRepository.findAll();
        assertThat(disponibilidadHorariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDisponibilidadHorarias() throws Exception {
        // Initialize the database
        disponibilidadHorariaRepository.saveAndFlush(disponibilidadHoraria);

        // Get all the disponibilidadHorariaList
        restDisponibilidadHorariaMockMvc.perform(get("/api/disponibilidad-horarias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disponibilidadHoraria.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDisponibilidadHoraria() throws Exception {
        // Initialize the database
        disponibilidadHorariaRepository.saveAndFlush(disponibilidadHoraria);

        // Get the disponibilidadHoraria
        restDisponibilidadHorariaMockMvc.perform(get("/api/disponibilidad-horarias/{id}", disponibilidadHoraria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disponibilidadHoraria.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDisponibilidadHoraria() throws Exception {
        // Get the disponibilidadHoraria
        restDisponibilidadHorariaMockMvc.perform(get("/api/disponibilidad-horarias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisponibilidadHoraria() throws Exception {
        // Initialize the database
        disponibilidadHorariaRepository.saveAndFlush(disponibilidadHoraria);

        int databaseSizeBeforeUpdate = disponibilidadHorariaRepository.findAll().size();

        // Update the disponibilidadHoraria
        DisponibilidadHoraria updatedDisponibilidadHoraria = disponibilidadHorariaRepository.findById(disponibilidadHoraria.getId()).get();
        // Disconnect from session so that the updates on updatedDisponibilidadHoraria are not directly saved in db
        em.detach(updatedDisponibilidadHoraria);

        restDisponibilidadHorariaMockMvc.perform(put("/api/disponibilidad-horarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisponibilidadHoraria)))
            .andExpect(status().isOk());

        // Validate the DisponibilidadHoraria in the database
        List<DisponibilidadHoraria> disponibilidadHorariaList = disponibilidadHorariaRepository.findAll();
        assertThat(disponibilidadHorariaList).hasSize(databaseSizeBeforeUpdate);
        DisponibilidadHoraria testDisponibilidadHoraria = disponibilidadHorariaList.get(disponibilidadHorariaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDisponibilidadHoraria() throws Exception {
        int databaseSizeBeforeUpdate = disponibilidadHorariaRepository.findAll().size();

        // Create the DisponibilidadHoraria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisponibilidadHorariaMockMvc.perform(put("/api/disponibilidad-horarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disponibilidadHoraria)))
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadHoraria in the database
        List<DisponibilidadHoraria> disponibilidadHorariaList = disponibilidadHorariaRepository.findAll();
        assertThat(disponibilidadHorariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisponibilidadHoraria() throws Exception {
        // Initialize the database
        disponibilidadHorariaRepository.saveAndFlush(disponibilidadHoraria);

        int databaseSizeBeforeDelete = disponibilidadHorariaRepository.findAll().size();

        // Delete the disponibilidadHoraria
        restDisponibilidadHorariaMockMvc.perform(delete("/api/disponibilidad-horarias/{id}", disponibilidadHoraria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DisponibilidadHoraria> disponibilidadHorariaList = disponibilidadHorariaRepository.findAll();
        assertThat(disponibilidadHorariaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisponibilidadHoraria.class);
        DisponibilidadHoraria disponibilidadHoraria1 = new DisponibilidadHoraria();
        disponibilidadHoraria1.setId(1L);
        DisponibilidadHoraria disponibilidadHoraria2 = new DisponibilidadHoraria();
        disponibilidadHoraria2.setId(disponibilidadHoraria1.getId());
        assertThat(disponibilidadHoraria1).isEqualTo(disponibilidadHoraria2);
        disponibilidadHoraria2.setId(2L);
        assertThat(disponibilidadHoraria1).isNotEqualTo(disponibilidadHoraria2);
        disponibilidadHoraria1.setId(null);
        assertThat(disponibilidadHoraria1).isNotEqualTo(disponibilidadHoraria2);
    }
}
