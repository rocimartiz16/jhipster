package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.VinculacionInstructor;
import co.edu.sena.ghostceet.domain.Instructor;
import co.edu.sena.ghostceet.domain.Anio;
import co.edu.sena.ghostceet.domain.Vinculacion;
import co.edu.sena.ghostceet.repository.VinculacionInstructorRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static co.edu.sena.ghostceet.web.rest.TestUtil.sameInstant;
import static co.edu.sena.ghostceet.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VinculacionInstructorResource REST controller.
 *
 * @see VinculacionInstructorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class VinculacionInstructorResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private VinculacionInstructorRepository vinculacionInstructorRepository;

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

    private MockMvc restVinculacionInstructorMockMvc;

    private VinculacionInstructor vinculacionInstructor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VinculacionInstructorResource vinculacionInstructorResource = new VinculacionInstructorResource(vinculacionInstructorRepository);
        this.restVinculacionInstructorMockMvc = MockMvcBuilders.standaloneSetup(vinculacionInstructorResource)
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
    public static VinculacionInstructor createEntity(EntityManager em) {
        VinculacionInstructor vinculacionInstructor = new VinculacionInstructor()
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN);
        // Add required entity
        Instructor instructor = InstructorResourceIntTest.createEntity(em);
        em.persist(instructor);
        em.flush();
        vinculacionInstructor.setInstructor(instructor);
        // Add required entity
        Anio anio = AnioResourceIntTest.createEntity(em);
        em.persist(anio);
        em.flush();
        vinculacionInstructor.setAnio1(anio);
        // Add required entity
        Vinculacion vinculacion = VinculacionResourceIntTest.createEntity(em);
        em.persist(vinculacion);
        em.flush();
        vinculacionInstructor.setVinculacion(vinculacion);
        return vinculacionInstructor;
    }

    @Before
    public void initTest() {
        vinculacionInstructor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVinculacionInstructor() throws Exception {
        int databaseSizeBeforeCreate = vinculacionInstructorRepository.findAll().size();

        // Create the VinculacionInstructor
        restVinculacionInstructorMockMvc.perform(post("/api/vinculacion-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculacionInstructor)))
            .andExpect(status().isCreated());

        // Validate the VinculacionInstructor in the database
        List<VinculacionInstructor> vinculacionInstructorList = vinculacionInstructorRepository.findAll();
        assertThat(vinculacionInstructorList).hasSize(databaseSizeBeforeCreate + 1);
        VinculacionInstructor testVinculacionInstructor = vinculacionInstructorList.get(vinculacionInstructorList.size() - 1);
        assertThat(testVinculacionInstructor.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testVinculacionInstructor.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
    }

    @Test
    @Transactional
    public void createVinculacionInstructorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vinculacionInstructorRepository.findAll().size();

        // Create the VinculacionInstructor with an existing ID
        vinculacionInstructor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVinculacionInstructorMockMvc.perform(post("/api/vinculacion-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculacionInstructor)))
            .andExpect(status().isBadRequest());

        // Validate the VinculacionInstructor in the database
        List<VinculacionInstructor> vinculacionInstructorList = vinculacionInstructorRepository.findAll();
        assertThat(vinculacionInstructorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = vinculacionInstructorRepository.findAll().size();
        // set the field null
        vinculacionInstructor.setFechaInicio(null);

        // Create the VinculacionInstructor, which fails.

        restVinculacionInstructorMockMvc.perform(post("/api/vinculacion-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculacionInstructor)))
            .andExpect(status().isBadRequest());

        List<VinculacionInstructor> vinculacionInstructorList = vinculacionInstructorRepository.findAll();
        assertThat(vinculacionInstructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = vinculacionInstructorRepository.findAll().size();
        // set the field null
        vinculacionInstructor.setFechaFin(null);

        // Create the VinculacionInstructor, which fails.

        restVinculacionInstructorMockMvc.perform(post("/api/vinculacion-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculacionInstructor)))
            .andExpect(status().isBadRequest());

        List<VinculacionInstructor> vinculacionInstructorList = vinculacionInstructorRepository.findAll();
        assertThat(vinculacionInstructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVinculacionInstructors() throws Exception {
        // Initialize the database
        vinculacionInstructorRepository.saveAndFlush(vinculacionInstructor);

        // Get all the vinculacionInstructorList
        restVinculacionInstructorMockMvc.perform(get("/api/vinculacion-instructors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vinculacionInstructor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(sameInstant(DEFAULT_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(sameInstant(DEFAULT_FECHA_FIN))));
    }
    
    @Test
    @Transactional
    public void getVinculacionInstructor() throws Exception {
        // Initialize the database
        vinculacionInstructorRepository.saveAndFlush(vinculacionInstructor);

        // Get the vinculacionInstructor
        restVinculacionInstructorMockMvc.perform(get("/api/vinculacion-instructors/{id}", vinculacionInstructor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vinculacionInstructor.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicio").value(sameInstant(DEFAULT_FECHA_INICIO)))
            .andExpect(jsonPath("$.fechaFin").value(sameInstant(DEFAULT_FECHA_FIN)));
    }

    @Test
    @Transactional
    public void getNonExistingVinculacionInstructor() throws Exception {
        // Get the vinculacionInstructor
        restVinculacionInstructorMockMvc.perform(get("/api/vinculacion-instructors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVinculacionInstructor() throws Exception {
        // Initialize the database
        vinculacionInstructorRepository.saveAndFlush(vinculacionInstructor);

        int databaseSizeBeforeUpdate = vinculacionInstructorRepository.findAll().size();

        // Update the vinculacionInstructor
        VinculacionInstructor updatedVinculacionInstructor = vinculacionInstructorRepository.findById(vinculacionInstructor.getId()).get();
        // Disconnect from session so that the updates on updatedVinculacionInstructor are not directly saved in db
        em.detach(updatedVinculacionInstructor);
        updatedVinculacionInstructor
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);

        restVinculacionInstructorMockMvc.perform(put("/api/vinculacion-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVinculacionInstructor)))
            .andExpect(status().isOk());

        // Validate the VinculacionInstructor in the database
        List<VinculacionInstructor> vinculacionInstructorList = vinculacionInstructorRepository.findAll();
        assertThat(vinculacionInstructorList).hasSize(databaseSizeBeforeUpdate);
        VinculacionInstructor testVinculacionInstructor = vinculacionInstructorList.get(vinculacionInstructorList.size() - 1);
        assertThat(testVinculacionInstructor.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testVinculacionInstructor.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingVinculacionInstructor() throws Exception {
        int databaseSizeBeforeUpdate = vinculacionInstructorRepository.findAll().size();

        // Create the VinculacionInstructor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVinculacionInstructorMockMvc.perform(put("/api/vinculacion-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculacionInstructor)))
            .andExpect(status().isBadRequest());

        // Validate the VinculacionInstructor in the database
        List<VinculacionInstructor> vinculacionInstructorList = vinculacionInstructorRepository.findAll();
        assertThat(vinculacionInstructorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVinculacionInstructor() throws Exception {
        // Initialize the database
        vinculacionInstructorRepository.saveAndFlush(vinculacionInstructor);

        int databaseSizeBeforeDelete = vinculacionInstructorRepository.findAll().size();

        // Delete the vinculacionInstructor
        restVinculacionInstructorMockMvc.perform(delete("/api/vinculacion-instructors/{id}", vinculacionInstructor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VinculacionInstructor> vinculacionInstructorList = vinculacionInstructorRepository.findAll();
        assertThat(vinculacionInstructorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VinculacionInstructor.class);
        VinculacionInstructor vinculacionInstructor1 = new VinculacionInstructor();
        vinculacionInstructor1.setId(1L);
        VinculacionInstructor vinculacionInstructor2 = new VinculacionInstructor();
        vinculacionInstructor2.setId(vinculacionInstructor1.getId());
        assertThat(vinculacionInstructor1).isEqualTo(vinculacionInstructor2);
        vinculacionInstructor2.setId(2L);
        assertThat(vinculacionInstructor1).isNotEqualTo(vinculacionInstructor2);
        vinculacionInstructor1.setId(null);
        assertThat(vinculacionInstructor1).isNotEqualTo(vinculacionInstructor2);
    }
}
