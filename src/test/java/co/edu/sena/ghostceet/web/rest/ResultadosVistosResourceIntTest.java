package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.ResultadosVistos;
import co.edu.sena.ghostceet.domain.ResultadoAprendizaje;
import co.edu.sena.ghostceet.domain.FichaTrimestre;
import co.edu.sena.ghostceet.repository.ResultadosVistosRepository;
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
 * Test class for the ResultadosVistosResource REST controller.
 *
 * @see ResultadosVistosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class ResultadosVistosResourceIntTest {

    @Autowired
    private ResultadosVistosRepository resultadosVistosRepository;

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

    private MockMvc restResultadosVistosMockMvc;

    private ResultadosVistos resultadosVistos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultadosVistosResource resultadosVistosResource = new ResultadosVistosResource(resultadosVistosRepository);
        this.restResultadosVistosMockMvc = MockMvcBuilders.standaloneSetup(resultadosVistosResource)
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
    public static ResultadosVistos createEntity(EntityManager em) {
        ResultadosVistos resultadosVistos = new ResultadosVistos();
        // Add required entity
        ResultadoAprendizaje resultadoAprendizaje = ResultadoAprendizajeResourceIntTest.createEntity(em);
        em.persist(resultadoAprendizaje);
        em.flush();
        resultadosVistos.setResultadoAprendizaje(resultadoAprendizaje);
        // Add required entity
        FichaTrimestre fichaTrimestre = FichaTrimestreResourceIntTest.createEntity(em);
        em.persist(fichaTrimestre);
        em.flush();
        resultadosVistos.setFichaTrimestre(fichaTrimestre);
        return resultadosVistos;
    }

    @Before
    public void initTest() {
        resultadosVistos = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultadosVistos() throws Exception {
        int databaseSizeBeforeCreate = resultadosVistosRepository.findAll().size();

        // Create the ResultadosVistos
        restResultadosVistosMockMvc.perform(post("/api/resultados-vistos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadosVistos)))
            .andExpect(status().isCreated());

        // Validate the ResultadosVistos in the database
        List<ResultadosVistos> resultadosVistosList = resultadosVistosRepository.findAll();
        assertThat(resultadosVistosList).hasSize(databaseSizeBeforeCreate + 1);
        ResultadosVistos testResultadosVistos = resultadosVistosList.get(resultadosVistosList.size() - 1);
    }

    @Test
    @Transactional
    public void createResultadosVistosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadosVistosRepository.findAll().size();

        // Create the ResultadosVistos with an existing ID
        resultadosVistos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadosVistosMockMvc.perform(post("/api/resultados-vistos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadosVistos)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadosVistos in the database
        List<ResultadosVistos> resultadosVistosList = resultadosVistosRepository.findAll();
        assertThat(resultadosVistosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResultadosVistos() throws Exception {
        // Initialize the database
        resultadosVistosRepository.saveAndFlush(resultadosVistos);

        // Get all the resultadosVistosList
        restResultadosVistosMockMvc.perform(get("/api/resultados-vistos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadosVistos.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getResultadosVistos() throws Exception {
        // Initialize the database
        resultadosVistosRepository.saveAndFlush(resultadosVistos);

        // Get the resultadosVistos
        restResultadosVistosMockMvc.perform(get("/api/resultados-vistos/{id}", resultadosVistos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultadosVistos.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResultadosVistos() throws Exception {
        // Get the resultadosVistos
        restResultadosVistosMockMvc.perform(get("/api/resultados-vistos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultadosVistos() throws Exception {
        // Initialize the database
        resultadosVistosRepository.saveAndFlush(resultadosVistos);

        int databaseSizeBeforeUpdate = resultadosVistosRepository.findAll().size();

        // Update the resultadosVistos
        ResultadosVistos updatedResultadosVistos = resultadosVistosRepository.findById(resultadosVistos.getId()).get();
        // Disconnect from session so that the updates on updatedResultadosVistos are not directly saved in db
        em.detach(updatedResultadosVistos);

        restResultadosVistosMockMvc.perform(put("/api/resultados-vistos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultadosVistos)))
            .andExpect(status().isOk());

        // Validate the ResultadosVistos in the database
        List<ResultadosVistos> resultadosVistosList = resultadosVistosRepository.findAll();
        assertThat(resultadosVistosList).hasSize(databaseSizeBeforeUpdate);
        ResultadosVistos testResultadosVistos = resultadosVistosList.get(resultadosVistosList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingResultadosVistos() throws Exception {
        int databaseSizeBeforeUpdate = resultadosVistosRepository.findAll().size();

        // Create the ResultadosVistos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadosVistosMockMvc.perform(put("/api/resultados-vistos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadosVistos)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadosVistos in the database
        List<ResultadosVistos> resultadosVistosList = resultadosVistosRepository.findAll();
        assertThat(resultadosVistosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultadosVistos() throws Exception {
        // Initialize the database
        resultadosVistosRepository.saveAndFlush(resultadosVistos);

        int databaseSizeBeforeDelete = resultadosVistosRepository.findAll().size();

        // Delete the resultadosVistos
        restResultadosVistosMockMvc.perform(delete("/api/resultados-vistos/{id}", resultadosVistos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResultadosVistos> resultadosVistosList = resultadosVistosRepository.findAll();
        assertThat(resultadosVistosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadosVistos.class);
        ResultadosVistos resultadosVistos1 = new ResultadosVistos();
        resultadosVistos1.setId(1L);
        ResultadosVistos resultadosVistos2 = new ResultadosVistos();
        resultadosVistos2.setId(resultadosVistos1.getId());
        assertThat(resultadosVistos1).isEqualTo(resultadosVistos2);
        resultadosVistos2.setId(2L);
        assertThat(resultadosVistos1).isNotEqualTo(resultadosVistos2);
        resultadosVistos1.setId(null);
        assertThat(resultadosVistos1).isNotEqualTo(resultadosVistos2);
    }
}
