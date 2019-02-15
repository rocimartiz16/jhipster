package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.JornadaInstructor;
import co.edu.sena.ghostceet.repository.JornadaInstructorRepository;
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

import co.edu.sena.ghostceet.domain.enumeration.Estado;
/**
 * Test class for the JornadaInstructorResource REST controller.
 *
 * @see JornadaInstructorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class JornadaInstructorResourceIntTest {

    private static final String DEFAULT_NOMBRE_JORNADA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_JORNADA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.Habilitado;
    private static final Estado UPDATED_ESTADO = Estado.Inhabilitado;

    @Autowired
    private JornadaInstructorRepository jornadaInstructorRepository;

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

    private MockMvc restJornadaInstructorMockMvc;

    private JornadaInstructor jornadaInstructor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JornadaInstructorResource jornadaInstructorResource = new JornadaInstructorResource(jornadaInstructorRepository);
        this.restJornadaInstructorMockMvc = MockMvcBuilders.standaloneSetup(jornadaInstructorResource)
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
    public static JornadaInstructor createEntity(EntityManager em) {
        JornadaInstructor jornadaInstructor = new JornadaInstructor()
            .nombreJornada(DEFAULT_NOMBRE_JORNADA)
            .descripcion(DEFAULT_DESCRIPCION)
            .estado(DEFAULT_ESTADO);
        return jornadaInstructor;
    }

    @Before
    public void initTest() {
        jornadaInstructor = createEntity(em);
    }

    @Test
    @Transactional
    public void createJornadaInstructor() throws Exception {
        int databaseSizeBeforeCreate = jornadaInstructorRepository.findAll().size();

        // Create the JornadaInstructor
        restJornadaInstructorMockMvc.perform(post("/api/jornada-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornadaInstructor)))
            .andExpect(status().isCreated());

        // Validate the JornadaInstructor in the database
        List<JornadaInstructor> jornadaInstructorList = jornadaInstructorRepository.findAll();
        assertThat(jornadaInstructorList).hasSize(databaseSizeBeforeCreate + 1);
        JornadaInstructor testJornadaInstructor = jornadaInstructorList.get(jornadaInstructorList.size() - 1);
        assertThat(testJornadaInstructor.getNombreJornada()).isEqualTo(DEFAULT_NOMBRE_JORNADA);
        assertThat(testJornadaInstructor.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testJornadaInstructor.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createJornadaInstructorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jornadaInstructorRepository.findAll().size();

        // Create the JornadaInstructor with an existing ID
        jornadaInstructor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJornadaInstructorMockMvc.perform(post("/api/jornada-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornadaInstructor)))
            .andExpect(status().isBadRequest());

        // Validate the JornadaInstructor in the database
        List<JornadaInstructor> jornadaInstructorList = jornadaInstructorRepository.findAll();
        assertThat(jornadaInstructorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreJornadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = jornadaInstructorRepository.findAll().size();
        // set the field null
        jornadaInstructor.setNombreJornada(null);

        // Create the JornadaInstructor, which fails.

        restJornadaInstructorMockMvc.perform(post("/api/jornada-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornadaInstructor)))
            .andExpect(status().isBadRequest());

        List<JornadaInstructor> jornadaInstructorList = jornadaInstructorRepository.findAll();
        assertThat(jornadaInstructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jornadaInstructorRepository.findAll().size();
        // set the field null
        jornadaInstructor.setDescripcion(null);

        // Create the JornadaInstructor, which fails.

        restJornadaInstructorMockMvc.perform(post("/api/jornada-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornadaInstructor)))
            .andExpect(status().isBadRequest());

        List<JornadaInstructor> jornadaInstructorList = jornadaInstructorRepository.findAll();
        assertThat(jornadaInstructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = jornadaInstructorRepository.findAll().size();
        // set the field null
        jornadaInstructor.setEstado(null);

        // Create the JornadaInstructor, which fails.

        restJornadaInstructorMockMvc.perform(post("/api/jornada-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornadaInstructor)))
            .andExpect(status().isBadRequest());

        List<JornadaInstructor> jornadaInstructorList = jornadaInstructorRepository.findAll();
        assertThat(jornadaInstructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJornadaInstructors() throws Exception {
        // Initialize the database
        jornadaInstructorRepository.saveAndFlush(jornadaInstructor);

        // Get all the jornadaInstructorList
        restJornadaInstructorMockMvc.perform(get("/api/jornada-instructors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jornadaInstructor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreJornada").value(hasItem(DEFAULT_NOMBRE_JORNADA.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getJornadaInstructor() throws Exception {
        // Initialize the database
        jornadaInstructorRepository.saveAndFlush(jornadaInstructor);

        // Get the jornadaInstructor
        restJornadaInstructorMockMvc.perform(get("/api/jornada-instructors/{id}", jornadaInstructor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jornadaInstructor.getId().intValue()))
            .andExpect(jsonPath("$.nombreJornada").value(DEFAULT_NOMBRE_JORNADA.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJornadaInstructor() throws Exception {
        // Get the jornadaInstructor
        restJornadaInstructorMockMvc.perform(get("/api/jornada-instructors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJornadaInstructor() throws Exception {
        // Initialize the database
        jornadaInstructorRepository.saveAndFlush(jornadaInstructor);

        int databaseSizeBeforeUpdate = jornadaInstructorRepository.findAll().size();

        // Update the jornadaInstructor
        JornadaInstructor updatedJornadaInstructor = jornadaInstructorRepository.findById(jornadaInstructor.getId()).get();
        // Disconnect from session so that the updates on updatedJornadaInstructor are not directly saved in db
        em.detach(updatedJornadaInstructor);
        updatedJornadaInstructor
            .nombreJornada(UPDATED_NOMBRE_JORNADA)
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO);

        restJornadaInstructorMockMvc.perform(put("/api/jornada-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJornadaInstructor)))
            .andExpect(status().isOk());

        // Validate the JornadaInstructor in the database
        List<JornadaInstructor> jornadaInstructorList = jornadaInstructorRepository.findAll();
        assertThat(jornadaInstructorList).hasSize(databaseSizeBeforeUpdate);
        JornadaInstructor testJornadaInstructor = jornadaInstructorList.get(jornadaInstructorList.size() - 1);
        assertThat(testJornadaInstructor.getNombreJornada()).isEqualTo(UPDATED_NOMBRE_JORNADA);
        assertThat(testJornadaInstructor.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testJornadaInstructor.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingJornadaInstructor() throws Exception {
        int databaseSizeBeforeUpdate = jornadaInstructorRepository.findAll().size();

        // Create the JornadaInstructor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJornadaInstructorMockMvc.perform(put("/api/jornada-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornadaInstructor)))
            .andExpect(status().isBadRequest());

        // Validate the JornadaInstructor in the database
        List<JornadaInstructor> jornadaInstructorList = jornadaInstructorRepository.findAll();
        assertThat(jornadaInstructorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJornadaInstructor() throws Exception {
        // Initialize the database
        jornadaInstructorRepository.saveAndFlush(jornadaInstructor);

        int databaseSizeBeforeDelete = jornadaInstructorRepository.findAll().size();

        // Delete the jornadaInstructor
        restJornadaInstructorMockMvc.perform(delete("/api/jornada-instructors/{id}", jornadaInstructor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JornadaInstructor> jornadaInstructorList = jornadaInstructorRepository.findAll();
        assertThat(jornadaInstructorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JornadaInstructor.class);
        JornadaInstructor jornadaInstructor1 = new JornadaInstructor();
        jornadaInstructor1.setId(1L);
        JornadaInstructor jornadaInstructor2 = new JornadaInstructor();
        jornadaInstructor2.setId(jornadaInstructor1.getId());
        assertThat(jornadaInstructor1).isEqualTo(jornadaInstructor2);
        jornadaInstructor2.setId(2L);
        assertThat(jornadaInstructor1).isNotEqualTo(jornadaInstructor2);
        jornadaInstructor1.setId(null);
        assertThat(jornadaInstructor1).isNotEqualTo(jornadaInstructor2);
    }
}
