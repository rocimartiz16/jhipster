package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.PlaneacionActividad;
import co.edu.sena.ghostceet.domain.PlaneacionTrimestre;
import co.edu.sena.ghostceet.domain.ActividadProyecto;
import co.edu.sena.ghostceet.repository.PlaneacionActividadRepository;
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
 * Test class for the PlaneacionActividadResource REST controller.
 *
 * @see PlaneacionActividadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class PlaneacionActividadResourceIntTest {

    @Autowired
    private PlaneacionActividadRepository planeacionActividadRepository;

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

    private MockMvc restPlaneacionActividadMockMvc;

    private PlaneacionActividad planeacionActividad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlaneacionActividadResource planeacionActividadResource = new PlaneacionActividadResource(planeacionActividadRepository);
        this.restPlaneacionActividadMockMvc = MockMvcBuilders.standaloneSetup(planeacionActividadResource)
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
    public static PlaneacionActividad createEntity(EntityManager em) {
        PlaneacionActividad planeacionActividad = new PlaneacionActividad();
        // Add required entity
        PlaneacionTrimestre planeacionTrimestre = PlaneacionTrimestreResourceIntTest.createEntity(em);
        em.persist(planeacionTrimestre);
        em.flush();
        planeacionActividad.setPlaneacionTrimestre(planeacionTrimestre);
        // Add required entity
        ActividadProyecto actividadProyecto = ActividadProyectoResourceIntTest.createEntity(em);
        em.persist(actividadProyecto);
        em.flush();
        planeacionActividad.setActividadProyecto(actividadProyecto);
        return planeacionActividad;
    }

    @Before
    public void initTest() {
        planeacionActividad = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaneacionActividad() throws Exception {
        int databaseSizeBeforeCreate = planeacionActividadRepository.findAll().size();

        // Create the PlaneacionActividad
        restPlaneacionActividadMockMvc.perform(post("/api/planeacion-actividads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacionActividad)))
            .andExpect(status().isCreated());

        // Validate the PlaneacionActividad in the database
        List<PlaneacionActividad> planeacionActividadList = planeacionActividadRepository.findAll();
        assertThat(planeacionActividadList).hasSize(databaseSizeBeforeCreate + 1);
        PlaneacionActividad testPlaneacionActividad = planeacionActividadList.get(planeacionActividadList.size() - 1);
    }

    @Test
    @Transactional
    public void createPlaneacionActividadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planeacionActividadRepository.findAll().size();

        // Create the PlaneacionActividad with an existing ID
        planeacionActividad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaneacionActividadMockMvc.perform(post("/api/planeacion-actividads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacionActividad)))
            .andExpect(status().isBadRequest());

        // Validate the PlaneacionActividad in the database
        List<PlaneacionActividad> planeacionActividadList = planeacionActividadRepository.findAll();
        assertThat(planeacionActividadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlaneacionActividads() throws Exception {
        // Initialize the database
        planeacionActividadRepository.saveAndFlush(planeacionActividad);

        // Get all the planeacionActividadList
        restPlaneacionActividadMockMvc.perform(get("/api/planeacion-actividads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planeacionActividad.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPlaneacionActividad() throws Exception {
        // Initialize the database
        planeacionActividadRepository.saveAndFlush(planeacionActividad);

        // Get the planeacionActividad
        restPlaneacionActividadMockMvc.perform(get("/api/planeacion-actividads/{id}", planeacionActividad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planeacionActividad.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaneacionActividad() throws Exception {
        // Get the planeacionActividad
        restPlaneacionActividadMockMvc.perform(get("/api/planeacion-actividads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaneacionActividad() throws Exception {
        // Initialize the database
        planeacionActividadRepository.saveAndFlush(planeacionActividad);

        int databaseSizeBeforeUpdate = planeacionActividadRepository.findAll().size();

        // Update the planeacionActividad
        PlaneacionActividad updatedPlaneacionActividad = planeacionActividadRepository.findById(planeacionActividad.getId()).get();
        // Disconnect from session so that the updates on updatedPlaneacionActividad are not directly saved in db
        em.detach(updatedPlaneacionActividad);

        restPlaneacionActividadMockMvc.perform(put("/api/planeacion-actividads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlaneacionActividad)))
            .andExpect(status().isOk());

        // Validate the PlaneacionActividad in the database
        List<PlaneacionActividad> planeacionActividadList = planeacionActividadRepository.findAll();
        assertThat(planeacionActividadList).hasSize(databaseSizeBeforeUpdate);
        PlaneacionActividad testPlaneacionActividad = planeacionActividadList.get(planeacionActividadList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaneacionActividad() throws Exception {
        int databaseSizeBeforeUpdate = planeacionActividadRepository.findAll().size();

        // Create the PlaneacionActividad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaneacionActividadMockMvc.perform(put("/api/planeacion-actividads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacionActividad)))
            .andExpect(status().isBadRequest());

        // Validate the PlaneacionActividad in the database
        List<PlaneacionActividad> planeacionActividadList = planeacionActividadRepository.findAll();
        assertThat(planeacionActividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlaneacionActividad() throws Exception {
        // Initialize the database
        planeacionActividadRepository.saveAndFlush(planeacionActividad);

        int databaseSizeBeforeDelete = planeacionActividadRepository.findAll().size();

        // Delete the planeacionActividad
        restPlaneacionActividadMockMvc.perform(delete("/api/planeacion-actividads/{id}", planeacionActividad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlaneacionActividad> planeacionActividadList = planeacionActividadRepository.findAll();
        assertThat(planeacionActividadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaneacionActividad.class);
        PlaneacionActividad planeacionActividad1 = new PlaneacionActividad();
        planeacionActividad1.setId(1L);
        PlaneacionActividad planeacionActividad2 = new PlaneacionActividad();
        planeacionActividad2.setId(planeacionActividad1.getId());
        assertThat(planeacionActividad1).isEqualTo(planeacionActividad2);
        planeacionActividad2.setId(2L);
        assertThat(planeacionActividad1).isNotEqualTo(planeacionActividad2);
        planeacionActividad1.setId(null);
        assertThat(planeacionActividad1).isNotEqualTo(planeacionActividad2);
    }
}
