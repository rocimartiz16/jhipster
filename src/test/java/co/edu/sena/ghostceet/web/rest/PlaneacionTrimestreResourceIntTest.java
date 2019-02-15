package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.PlaneacionTrimestre;
import co.edu.sena.ghostceet.domain.Trimestre;
import co.edu.sena.ghostceet.repository.PlaneacionTrimestreRepository;
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
 * Test class for the PlaneacionTrimestreResource REST controller.
 *
 * @see PlaneacionTrimestreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class PlaneacionTrimestreResourceIntTest {

    @Autowired
    private PlaneacionTrimestreRepository planeacionTrimestreRepository;

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

    private MockMvc restPlaneacionTrimestreMockMvc;

    private PlaneacionTrimestre planeacionTrimestre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlaneacionTrimestreResource planeacionTrimestreResource = new PlaneacionTrimestreResource(planeacionTrimestreRepository);
        this.restPlaneacionTrimestreMockMvc = MockMvcBuilders.standaloneSetup(planeacionTrimestreResource)
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
    public static PlaneacionTrimestre createEntity(EntityManager em) {
        PlaneacionTrimestre planeacionTrimestre = new PlaneacionTrimestre();
        // Add required entity
        Trimestre trimestre = TrimestreResourceIntTest.createEntity(em);
        em.persist(trimestre);
        em.flush();
        planeacionTrimestre.setTrimestre(trimestre);
        return planeacionTrimestre;
    }

    @Before
    public void initTest() {
        planeacionTrimestre = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaneacionTrimestre() throws Exception {
        int databaseSizeBeforeCreate = planeacionTrimestreRepository.findAll().size();

        // Create the PlaneacionTrimestre
        restPlaneacionTrimestreMockMvc.perform(post("/api/planeacion-trimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacionTrimestre)))
            .andExpect(status().isCreated());

        // Validate the PlaneacionTrimestre in the database
        List<PlaneacionTrimestre> planeacionTrimestreList = planeacionTrimestreRepository.findAll();
        assertThat(planeacionTrimestreList).hasSize(databaseSizeBeforeCreate + 1);
        PlaneacionTrimestre testPlaneacionTrimestre = planeacionTrimestreList.get(planeacionTrimestreList.size() - 1);
    }

    @Test
    @Transactional
    public void createPlaneacionTrimestreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planeacionTrimestreRepository.findAll().size();

        // Create the PlaneacionTrimestre with an existing ID
        planeacionTrimestre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaneacionTrimestreMockMvc.perform(post("/api/planeacion-trimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacionTrimestre)))
            .andExpect(status().isBadRequest());

        // Validate the PlaneacionTrimestre in the database
        List<PlaneacionTrimestre> planeacionTrimestreList = planeacionTrimestreRepository.findAll();
        assertThat(planeacionTrimestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlaneacionTrimestres() throws Exception {
        // Initialize the database
        planeacionTrimestreRepository.saveAndFlush(planeacionTrimestre);

        // Get all the planeacionTrimestreList
        restPlaneacionTrimestreMockMvc.perform(get("/api/planeacion-trimestres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planeacionTrimestre.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPlaneacionTrimestre() throws Exception {
        // Initialize the database
        planeacionTrimestreRepository.saveAndFlush(planeacionTrimestre);

        // Get the planeacionTrimestre
        restPlaneacionTrimestreMockMvc.perform(get("/api/planeacion-trimestres/{id}", planeacionTrimestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planeacionTrimestre.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaneacionTrimestre() throws Exception {
        // Get the planeacionTrimestre
        restPlaneacionTrimestreMockMvc.perform(get("/api/planeacion-trimestres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaneacionTrimestre() throws Exception {
        // Initialize the database
        planeacionTrimestreRepository.saveAndFlush(planeacionTrimestre);

        int databaseSizeBeforeUpdate = planeacionTrimestreRepository.findAll().size();

        // Update the planeacionTrimestre
        PlaneacionTrimestre updatedPlaneacionTrimestre = planeacionTrimestreRepository.findById(planeacionTrimestre.getId()).get();
        // Disconnect from session so that the updates on updatedPlaneacionTrimestre are not directly saved in db
        em.detach(updatedPlaneacionTrimestre);

        restPlaneacionTrimestreMockMvc.perform(put("/api/planeacion-trimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlaneacionTrimestre)))
            .andExpect(status().isOk());

        // Validate the PlaneacionTrimestre in the database
        List<PlaneacionTrimestre> planeacionTrimestreList = planeacionTrimestreRepository.findAll();
        assertThat(planeacionTrimestreList).hasSize(databaseSizeBeforeUpdate);
        PlaneacionTrimestre testPlaneacionTrimestre = planeacionTrimestreList.get(planeacionTrimestreList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaneacionTrimestre() throws Exception {
        int databaseSizeBeforeUpdate = planeacionTrimestreRepository.findAll().size();

        // Create the PlaneacionTrimestre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaneacionTrimestreMockMvc.perform(put("/api/planeacion-trimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeacionTrimestre)))
            .andExpect(status().isBadRequest());

        // Validate the PlaneacionTrimestre in the database
        List<PlaneacionTrimestre> planeacionTrimestreList = planeacionTrimestreRepository.findAll();
        assertThat(planeacionTrimestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlaneacionTrimestre() throws Exception {
        // Initialize the database
        planeacionTrimestreRepository.saveAndFlush(planeacionTrimestre);

        int databaseSizeBeforeDelete = planeacionTrimestreRepository.findAll().size();

        // Delete the planeacionTrimestre
        restPlaneacionTrimestreMockMvc.perform(delete("/api/planeacion-trimestres/{id}", planeacionTrimestre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlaneacionTrimestre> planeacionTrimestreList = planeacionTrimestreRepository.findAll();
        assertThat(planeacionTrimestreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaneacionTrimestre.class);
        PlaneacionTrimestre planeacionTrimestre1 = new PlaneacionTrimestre();
        planeacionTrimestre1.setId(1L);
        PlaneacionTrimestre planeacionTrimestre2 = new PlaneacionTrimestre();
        planeacionTrimestre2.setId(planeacionTrimestre1.getId());
        assertThat(planeacionTrimestre1).isEqualTo(planeacionTrimestre2);
        planeacionTrimestre2.setId(2L);
        assertThat(planeacionTrimestre1).isNotEqualTo(planeacionTrimestre2);
        planeacionTrimestre1.setId(null);
        assertThat(planeacionTrimestre1).isNotEqualTo(planeacionTrimestre2);
    }
}
