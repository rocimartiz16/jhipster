package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.FichaTrimestre;
import co.edu.sena.ghostceet.domain.Ficha;
import co.edu.sena.ghostceet.repository.FichaTrimestreRepository;
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
 * Test class for the FichaTrimestreResource REST controller.
 *
 * @see FichaTrimestreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class FichaTrimestreResourceIntTest {

    @Autowired
    private FichaTrimestreRepository fichaTrimestreRepository;

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

    private MockMvc restFichaTrimestreMockMvc;

    private FichaTrimestre fichaTrimestre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichaTrimestreResource fichaTrimestreResource = new FichaTrimestreResource(fichaTrimestreRepository);
        this.restFichaTrimestreMockMvc = MockMvcBuilders.standaloneSetup(fichaTrimestreResource)
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
    public static FichaTrimestre createEntity(EntityManager em) {
        FichaTrimestre fichaTrimestre = new FichaTrimestre();
        // Add required entity
        Ficha ficha = FichaResourceIntTest.createEntity(em);
        em.persist(ficha);
        em.flush();
        fichaTrimestre.setFicha(ficha);
        return fichaTrimestre;
    }

    @Before
    public void initTest() {
        fichaTrimestre = createEntity(em);
    }

    @Test
    @Transactional
    public void createFichaTrimestre() throws Exception {
        int databaseSizeBeforeCreate = fichaTrimestreRepository.findAll().size();

        // Create the FichaTrimestre
        restFichaTrimestreMockMvc.perform(post("/api/ficha-trimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaTrimestre)))
            .andExpect(status().isCreated());

        // Validate the FichaTrimestre in the database
        List<FichaTrimestre> fichaTrimestreList = fichaTrimestreRepository.findAll();
        assertThat(fichaTrimestreList).hasSize(databaseSizeBeforeCreate + 1);
        FichaTrimestre testFichaTrimestre = fichaTrimestreList.get(fichaTrimestreList.size() - 1);
    }

    @Test
    @Transactional
    public void createFichaTrimestreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichaTrimestreRepository.findAll().size();

        // Create the FichaTrimestre with an existing ID
        fichaTrimestre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichaTrimestreMockMvc.perform(post("/api/ficha-trimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaTrimestre)))
            .andExpect(status().isBadRequest());

        // Validate the FichaTrimestre in the database
        List<FichaTrimestre> fichaTrimestreList = fichaTrimestreRepository.findAll();
        assertThat(fichaTrimestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFichaTrimestres() throws Exception {
        // Initialize the database
        fichaTrimestreRepository.saveAndFlush(fichaTrimestre);

        // Get all the fichaTrimestreList
        restFichaTrimestreMockMvc.perform(get("/api/ficha-trimestres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichaTrimestre.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFichaTrimestre() throws Exception {
        // Initialize the database
        fichaTrimestreRepository.saveAndFlush(fichaTrimestre);

        // Get the fichaTrimestre
        restFichaTrimestreMockMvc.perform(get("/api/ficha-trimestres/{id}", fichaTrimestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fichaTrimestre.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFichaTrimestre() throws Exception {
        // Get the fichaTrimestre
        restFichaTrimestreMockMvc.perform(get("/api/ficha-trimestres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFichaTrimestre() throws Exception {
        // Initialize the database
        fichaTrimestreRepository.saveAndFlush(fichaTrimestre);

        int databaseSizeBeforeUpdate = fichaTrimestreRepository.findAll().size();

        // Update the fichaTrimestre
        FichaTrimestre updatedFichaTrimestre = fichaTrimestreRepository.findById(fichaTrimestre.getId()).get();
        // Disconnect from session so that the updates on updatedFichaTrimestre are not directly saved in db
        em.detach(updatedFichaTrimestre);

        restFichaTrimestreMockMvc.perform(put("/api/ficha-trimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFichaTrimestre)))
            .andExpect(status().isOk());

        // Validate the FichaTrimestre in the database
        List<FichaTrimestre> fichaTrimestreList = fichaTrimestreRepository.findAll();
        assertThat(fichaTrimestreList).hasSize(databaseSizeBeforeUpdate);
        FichaTrimestre testFichaTrimestre = fichaTrimestreList.get(fichaTrimestreList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFichaTrimestre() throws Exception {
        int databaseSizeBeforeUpdate = fichaTrimestreRepository.findAll().size();

        // Create the FichaTrimestre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichaTrimestreMockMvc.perform(put("/api/ficha-trimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaTrimestre)))
            .andExpect(status().isBadRequest());

        // Validate the FichaTrimestre in the database
        List<FichaTrimestre> fichaTrimestreList = fichaTrimestreRepository.findAll();
        assertThat(fichaTrimestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFichaTrimestre() throws Exception {
        // Initialize the database
        fichaTrimestreRepository.saveAndFlush(fichaTrimestre);

        int databaseSizeBeforeDelete = fichaTrimestreRepository.findAll().size();

        // Delete the fichaTrimestre
        restFichaTrimestreMockMvc.perform(delete("/api/ficha-trimestres/{id}", fichaTrimestre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FichaTrimestre> fichaTrimestreList = fichaTrimestreRepository.findAll();
        assertThat(fichaTrimestreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaTrimestre.class);
        FichaTrimestre fichaTrimestre1 = new FichaTrimestre();
        fichaTrimestre1.setId(1L);
        FichaTrimestre fichaTrimestre2 = new FichaTrimestre();
        fichaTrimestre2.setId(fichaTrimestre1.getId());
        assertThat(fichaTrimestre1).isEqualTo(fichaTrimestre2);
        fichaTrimestre2.setId(2L);
        assertThat(fichaTrimestre1).isNotEqualTo(fichaTrimestre2);
        fichaTrimestre1.setId(null);
        assertThat(fichaTrimestre1).isNotEqualTo(fichaTrimestre2);
    }
}
