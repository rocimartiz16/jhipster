package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.ActividadProyecto;
import co.edu.sena.ghostceet.domain.FaseProyecto;
import co.edu.sena.ghostceet.repository.ActividadProyectoRepository;
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
 * Test class for the ActividadProyectoResource REST controller.
 *
 * @see ActividadProyectoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class ActividadProyectoResourceIntTest {

    private static final Integer DEFAULT_NUMERO_ACTIVIDAD = 1;
    private static final Integer UPDATED_NUMERO_ACTIVIDAD = 2;

    private static final String DEFAULT_NOMBRE_ACTIVIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ACTIVIDAD = "BBBBBBBBBB";

    @Autowired
    private ActividadProyectoRepository actividadProyectoRepository;

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

    private MockMvc restActividadProyectoMockMvc;

    private ActividadProyecto actividadProyecto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActividadProyectoResource actividadProyectoResource = new ActividadProyectoResource(actividadProyectoRepository);
        this.restActividadProyectoMockMvc = MockMvcBuilders.standaloneSetup(actividadProyectoResource)
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
    public static ActividadProyecto createEntity(EntityManager em) {
        ActividadProyecto actividadProyecto = new ActividadProyecto()
            .numeroActividad(DEFAULT_NUMERO_ACTIVIDAD)
            .nombreActividad(DEFAULT_NOMBRE_ACTIVIDAD);
        // Add required entity
        FaseProyecto faseProyecto = FaseProyectoResourceIntTest.createEntity(em);
        em.persist(faseProyecto);
        em.flush();
        actividadProyecto.setFaseProyecto(faseProyecto);
        return actividadProyecto;
    }

    @Before
    public void initTest() {
        actividadProyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createActividadProyecto() throws Exception {
        int databaseSizeBeforeCreate = actividadProyectoRepository.findAll().size();

        // Create the ActividadProyecto
        restActividadProyectoMockMvc.perform(post("/api/actividad-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadProyecto)))
            .andExpect(status().isCreated());

        // Validate the ActividadProyecto in the database
        List<ActividadProyecto> actividadProyectoList = actividadProyectoRepository.findAll();
        assertThat(actividadProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        ActividadProyecto testActividadProyecto = actividadProyectoList.get(actividadProyectoList.size() - 1);
        assertThat(testActividadProyecto.getNumeroActividad()).isEqualTo(DEFAULT_NUMERO_ACTIVIDAD);
        assertThat(testActividadProyecto.getNombreActividad()).isEqualTo(DEFAULT_NOMBRE_ACTIVIDAD);
    }

    @Test
    @Transactional
    public void createActividadProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actividadProyectoRepository.findAll().size();

        // Create the ActividadProyecto with an existing ID
        actividadProyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActividadProyectoMockMvc.perform(post("/api/actividad-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the ActividadProyecto in the database
        List<ActividadProyecto> actividadProyectoList = actividadProyectoRepository.findAll();
        assertThat(actividadProyectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroActividadIsRequired() throws Exception {
        int databaseSizeBeforeTest = actividadProyectoRepository.findAll().size();
        // set the field null
        actividadProyecto.setNumeroActividad(null);

        // Create the ActividadProyecto, which fails.

        restActividadProyectoMockMvc.perform(post("/api/actividad-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadProyecto)))
            .andExpect(status().isBadRequest());

        List<ActividadProyecto> actividadProyectoList = actividadProyectoRepository.findAll();
        assertThat(actividadProyectoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreActividadIsRequired() throws Exception {
        int databaseSizeBeforeTest = actividadProyectoRepository.findAll().size();
        // set the field null
        actividadProyecto.setNombreActividad(null);

        // Create the ActividadProyecto, which fails.

        restActividadProyectoMockMvc.perform(post("/api/actividad-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadProyecto)))
            .andExpect(status().isBadRequest());

        List<ActividadProyecto> actividadProyectoList = actividadProyectoRepository.findAll();
        assertThat(actividadProyectoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActividadProyectos() throws Exception {
        // Initialize the database
        actividadProyectoRepository.saveAndFlush(actividadProyecto);

        // Get all the actividadProyectoList
        restActividadProyectoMockMvc.perform(get("/api/actividad-proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actividadProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroActividad").value(hasItem(DEFAULT_NUMERO_ACTIVIDAD)))
            .andExpect(jsonPath("$.[*].nombreActividad").value(hasItem(DEFAULT_NOMBRE_ACTIVIDAD.toString())));
    }
    
    @Test
    @Transactional
    public void getActividadProyecto() throws Exception {
        // Initialize the database
        actividadProyectoRepository.saveAndFlush(actividadProyecto);

        // Get the actividadProyecto
        restActividadProyectoMockMvc.perform(get("/api/actividad-proyectos/{id}", actividadProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actividadProyecto.getId().intValue()))
            .andExpect(jsonPath("$.numeroActividad").value(DEFAULT_NUMERO_ACTIVIDAD))
            .andExpect(jsonPath("$.nombreActividad").value(DEFAULT_NOMBRE_ACTIVIDAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActividadProyecto() throws Exception {
        // Get the actividadProyecto
        restActividadProyectoMockMvc.perform(get("/api/actividad-proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActividadProyecto() throws Exception {
        // Initialize the database
        actividadProyectoRepository.saveAndFlush(actividadProyecto);

        int databaseSizeBeforeUpdate = actividadProyectoRepository.findAll().size();

        // Update the actividadProyecto
        ActividadProyecto updatedActividadProyecto = actividadProyectoRepository.findById(actividadProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedActividadProyecto are not directly saved in db
        em.detach(updatedActividadProyecto);
        updatedActividadProyecto
            .numeroActividad(UPDATED_NUMERO_ACTIVIDAD)
            .nombreActividad(UPDATED_NOMBRE_ACTIVIDAD);

        restActividadProyectoMockMvc.perform(put("/api/actividad-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActividadProyecto)))
            .andExpect(status().isOk());

        // Validate the ActividadProyecto in the database
        List<ActividadProyecto> actividadProyectoList = actividadProyectoRepository.findAll();
        assertThat(actividadProyectoList).hasSize(databaseSizeBeforeUpdate);
        ActividadProyecto testActividadProyecto = actividadProyectoList.get(actividadProyectoList.size() - 1);
        assertThat(testActividadProyecto.getNumeroActividad()).isEqualTo(UPDATED_NUMERO_ACTIVIDAD);
        assertThat(testActividadProyecto.getNombreActividad()).isEqualTo(UPDATED_NOMBRE_ACTIVIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingActividadProyecto() throws Exception {
        int databaseSizeBeforeUpdate = actividadProyectoRepository.findAll().size();

        // Create the ActividadProyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActividadProyectoMockMvc.perform(put("/api/actividad-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the ActividadProyecto in the database
        List<ActividadProyecto> actividadProyectoList = actividadProyectoRepository.findAll();
        assertThat(actividadProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActividadProyecto() throws Exception {
        // Initialize the database
        actividadProyectoRepository.saveAndFlush(actividadProyecto);

        int databaseSizeBeforeDelete = actividadProyectoRepository.findAll().size();

        // Delete the actividadProyecto
        restActividadProyectoMockMvc.perform(delete("/api/actividad-proyectos/{id}", actividadProyecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActividadProyecto> actividadProyectoList = actividadProyectoRepository.findAll();
        assertThat(actividadProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActividadProyecto.class);
        ActividadProyecto actividadProyecto1 = new ActividadProyecto();
        actividadProyecto1.setId(1L);
        ActividadProyecto actividadProyecto2 = new ActividadProyecto();
        actividadProyecto2.setId(actividadProyecto1.getId());
        assertThat(actividadProyecto1).isEqualTo(actividadProyecto2);
        actividadProyecto2.setId(2L);
        assertThat(actividadProyecto1).isNotEqualTo(actividadProyecto2);
        actividadProyecto1.setId(null);
        assertThat(actividadProyecto1).isNotEqualTo(actividadProyecto2);
    }
}
