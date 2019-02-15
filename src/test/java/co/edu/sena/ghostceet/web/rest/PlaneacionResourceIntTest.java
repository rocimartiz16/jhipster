package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.Planeacion;
import co.edu.sena.ghostceet.repository.PlaneacionRepository;
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
 * Test class for the PlaneacionResource REST controller.
 *
 * @see PlaneacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class PlaneacionResourceIntTest {

    private static final String DEFAULT_NOMBRE_PLANEACION = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PLANEACION = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.Habilitado;
    private static final Estado UPDATED_ESTADO = Estado.Inhabilitado;

    @Autowired
    private PlaneacionRepository planeacionRepository;

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

    private MockMvc restPlaneacionMockMvc;

    private Planeacion planeacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlaneacionResource planeacionResource = new PlaneacionResource(planeacionRepository);
        this.restPlaneacionMockMvc = MockMvcBuilders.standaloneSetup(planeacionResource)
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
    public static Planeacion createEntity(EntityManager em) {
        Planeacion planeacion = new Planeacion()
            .nombrePlaneacion(DEFAULT_NOMBRE_PLANEACION)
            .estado(DEFAULT_ESTADO);
        return planeacion;
    }

    @Before
    public void initTest() {
        planeacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaneacion() throws Exception {
        int databaseSizeBeforeCreate = planeacionRepository.findAll().size();

        // Create the Planeacion
        restPlaneacionMockMvc.perform(post("/api/planeacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacion)))
            .andExpect(status().isCreated());

        // Validate the Planeacion in the database
        List<Planeacion> planeacionList = planeacionRepository.findAll();
        assertThat(planeacionList).hasSize(databaseSizeBeforeCreate + 1);
        Planeacion testPlaneacion = planeacionList.get(planeacionList.size() - 1);
        assertThat(testPlaneacion.getNombrePlaneacion()).isEqualTo(DEFAULT_NOMBRE_PLANEACION);
        assertThat(testPlaneacion.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createPlaneacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planeacionRepository.findAll().size();

        // Create the Planeacion with an existing ID
        planeacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaneacionMockMvc.perform(post("/api/planeacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacion)))
            .andExpect(status().isBadRequest());

        // Validate the Planeacion in the database
        List<Planeacion> planeacionList = planeacionRepository.findAll();
        assertThat(planeacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombrePlaneacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = planeacionRepository.findAll().size();
        // set the field null
        planeacion.setNombrePlaneacion(null);

        // Create the Planeacion, which fails.

        restPlaneacionMockMvc.perform(post("/api/planeacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacion)))
            .andExpect(status().isBadRequest());

        List<Planeacion> planeacionList = planeacionRepository.findAll();
        assertThat(planeacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planeacionRepository.findAll().size();
        // set the field null
        planeacion.setEstado(null);

        // Create the Planeacion, which fails.

        restPlaneacionMockMvc.perform(post("/api/planeacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacion)))
            .andExpect(status().isBadRequest());

        List<Planeacion> planeacionList = planeacionRepository.findAll();
        assertThat(planeacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlaneacions() throws Exception {
        // Initialize the database
        planeacionRepository.saveAndFlush(planeacion);

        // Get all the planeacionList
        restPlaneacionMockMvc.perform(get("/api/planeacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planeacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePlaneacion").value(hasItem(DEFAULT_NOMBRE_PLANEACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getPlaneacion() throws Exception {
        // Initialize the database
        planeacionRepository.saveAndFlush(planeacion);

        // Get the planeacion
        restPlaneacionMockMvc.perform(get("/api/planeacions/{id}", planeacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planeacion.getId().intValue()))
            .andExpect(jsonPath("$.nombrePlaneacion").value(DEFAULT_NOMBRE_PLANEACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaneacion() throws Exception {
        // Get the planeacion
        restPlaneacionMockMvc.perform(get("/api/planeacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaneacion() throws Exception {
        // Initialize the database
        planeacionRepository.saveAndFlush(planeacion);

        int databaseSizeBeforeUpdate = planeacionRepository.findAll().size();

        // Update the planeacion
        Planeacion updatedPlaneacion = planeacionRepository.findById(planeacion.getId()).get();
        // Disconnect from session so that the updates on updatedPlaneacion are not directly saved in db
        em.detach(updatedPlaneacion);
        updatedPlaneacion
            .nombrePlaneacion(UPDATED_NOMBRE_PLANEACION)
            .estado(UPDATED_ESTADO);

        restPlaneacionMockMvc.perform(put("/api/planeacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlaneacion)))
            .andExpect(status().isOk());

        // Validate the Planeacion in the database
        List<Planeacion> planeacionList = planeacionRepository.findAll();
        assertThat(planeacionList).hasSize(databaseSizeBeforeUpdate);
        Planeacion testPlaneacion = planeacionList.get(planeacionList.size() - 1);
        assertThat(testPlaneacion.getNombrePlaneacion()).isEqualTo(UPDATED_NOMBRE_PLANEACION);
        assertThat(testPlaneacion.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaneacion() throws Exception {
        int databaseSizeBeforeUpdate = planeacionRepository.findAll().size();

        // Create the Planeacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaneacionMockMvc.perform(put("/api/planeacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacion)))
            .andExpect(status().isBadRequest());

        // Validate the Planeacion in the database
        List<Planeacion> planeacionList = planeacionRepository.findAll();
        assertThat(planeacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlaneacion() throws Exception {
        // Initialize the database
        planeacionRepository.saveAndFlush(planeacion);

        int databaseSizeBeforeDelete = planeacionRepository.findAll().size();

        // Delete the planeacion
        restPlaneacionMockMvc.perform(delete("/api/planeacions/{id}", planeacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Planeacion> planeacionList = planeacionRepository.findAll();
        assertThat(planeacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Planeacion.class);
        Planeacion planeacion1 = new Planeacion();
        planeacion1.setId(1L);
        Planeacion planeacion2 = new Planeacion();
        planeacion2.setId(planeacion1.getId());
        assertThat(planeacion1).isEqualTo(planeacion2);
        planeacion2.setId(2L);
        assertThat(planeacion1).isNotEqualTo(planeacion2);
        planeacion1.setId(null);
        assertThat(planeacion1).isNotEqualTo(planeacion2);
    }
}
