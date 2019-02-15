package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.ResultadoAprendizaje;
import co.edu.sena.ghostceet.repository.ResultadoAprendizajeRepository;
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
 * Test class for the ResultadoAprendizajeResource REST controller.
 *
 * @see ResultadoAprendizajeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class ResultadoAprendizajeResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ResultadoAprendizajeRepository resultadoAprendizajeRepository;

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

    private MockMvc restResultadoAprendizajeMockMvc;

    private ResultadoAprendizaje resultadoAprendizaje;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultadoAprendizajeResource resultadoAprendizajeResource = new ResultadoAprendizajeResource(resultadoAprendizajeRepository);
        this.restResultadoAprendizajeMockMvc = MockMvcBuilders.standaloneSetup(resultadoAprendizajeResource)
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
    public static ResultadoAprendizaje createEntity(EntityManager em) {
        ResultadoAprendizaje resultadoAprendizaje = new ResultadoAprendizaje()
            .codigo(DEFAULT_CODIGO)
            .descripcion(DEFAULT_DESCRIPCION);
        return resultadoAprendizaje;
    }

    @Before
    public void initTest() {
        resultadoAprendizaje = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultadoAprendizaje() throws Exception {
        int databaseSizeBeforeCreate = resultadoAprendizajeRepository.findAll().size();

        // Create the ResultadoAprendizaje
        restResultadoAprendizajeMockMvc.perform(post("/api/resultado-aprendizajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAprendizaje)))
            .andExpect(status().isCreated());

        // Validate the ResultadoAprendizaje in the database
        List<ResultadoAprendizaje> resultadoAprendizajeList = resultadoAprendizajeRepository.findAll();
        assertThat(resultadoAprendizajeList).hasSize(databaseSizeBeforeCreate + 1);
        ResultadoAprendizaje testResultadoAprendizaje = resultadoAprendizajeList.get(resultadoAprendizajeList.size() - 1);
        assertThat(testResultadoAprendizaje.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testResultadoAprendizaje.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createResultadoAprendizajeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadoAprendizajeRepository.findAll().size();

        // Create the ResultadoAprendizaje with an existing ID
        resultadoAprendizaje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadoAprendizajeMockMvc.perform(post("/api/resultado-aprendizajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAprendizaje)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoAprendizaje in the database
        List<ResultadoAprendizaje> resultadoAprendizajeList = resultadoAprendizajeRepository.findAll();
        assertThat(resultadoAprendizajeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoAprendizajeRepository.findAll().size();
        // set the field null
        resultadoAprendizaje.setCodigo(null);

        // Create the ResultadoAprendizaje, which fails.

        restResultadoAprendizajeMockMvc.perform(post("/api/resultado-aprendizajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAprendizaje)))
            .andExpect(status().isBadRequest());

        List<ResultadoAprendizaje> resultadoAprendizajeList = resultadoAprendizajeRepository.findAll();
        assertThat(resultadoAprendizajeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoAprendizajeRepository.findAll().size();
        // set the field null
        resultadoAprendizaje.setDescripcion(null);

        // Create the ResultadoAprendizaje, which fails.

        restResultadoAprendizajeMockMvc.perform(post("/api/resultado-aprendizajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAprendizaje)))
            .andExpect(status().isBadRequest());

        List<ResultadoAprendizaje> resultadoAprendizajeList = resultadoAprendizajeRepository.findAll();
        assertThat(resultadoAprendizajeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultadoAprendizajes() throws Exception {
        // Initialize the database
        resultadoAprendizajeRepository.saveAndFlush(resultadoAprendizaje);

        // Get all the resultadoAprendizajeList
        restResultadoAprendizajeMockMvc.perform(get("/api/resultado-aprendizajes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoAprendizaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getResultadoAprendizaje() throws Exception {
        // Initialize the database
        resultadoAprendizajeRepository.saveAndFlush(resultadoAprendizaje);

        // Get the resultadoAprendizaje
        restResultadoAprendizajeMockMvc.perform(get("/api/resultado-aprendizajes/{id}", resultadoAprendizaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultadoAprendizaje.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResultadoAprendizaje() throws Exception {
        // Get the resultadoAprendizaje
        restResultadoAprendizajeMockMvc.perform(get("/api/resultado-aprendizajes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultadoAprendizaje() throws Exception {
        // Initialize the database
        resultadoAprendizajeRepository.saveAndFlush(resultadoAprendizaje);

        int databaseSizeBeforeUpdate = resultadoAprendizajeRepository.findAll().size();

        // Update the resultadoAprendizaje
        ResultadoAprendizaje updatedResultadoAprendizaje = resultadoAprendizajeRepository.findById(resultadoAprendizaje.getId()).get();
        // Disconnect from session so that the updates on updatedResultadoAprendizaje are not directly saved in db
        em.detach(updatedResultadoAprendizaje);
        updatedResultadoAprendizaje
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION);

        restResultadoAprendizajeMockMvc.perform(put("/api/resultado-aprendizajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultadoAprendizaje)))
            .andExpect(status().isOk());

        // Validate the ResultadoAprendizaje in the database
        List<ResultadoAprendizaje> resultadoAprendizajeList = resultadoAprendizajeRepository.findAll();
        assertThat(resultadoAprendizajeList).hasSize(databaseSizeBeforeUpdate);
        ResultadoAprendizaje testResultadoAprendizaje = resultadoAprendizajeList.get(resultadoAprendizajeList.size() - 1);
        assertThat(testResultadoAprendizaje.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testResultadoAprendizaje.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingResultadoAprendizaje() throws Exception {
        int databaseSizeBeforeUpdate = resultadoAprendizajeRepository.findAll().size();

        // Create the ResultadoAprendizaje

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadoAprendizajeMockMvc.perform(put("/api/resultado-aprendizajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAprendizaje)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoAprendizaje in the database
        List<ResultadoAprendizaje> resultadoAprendizajeList = resultadoAprendizajeRepository.findAll();
        assertThat(resultadoAprendizajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultadoAprendizaje() throws Exception {
        // Initialize the database
        resultadoAprendizajeRepository.saveAndFlush(resultadoAprendizaje);

        int databaseSizeBeforeDelete = resultadoAprendizajeRepository.findAll().size();

        // Delete the resultadoAprendizaje
        restResultadoAprendizajeMockMvc.perform(delete("/api/resultado-aprendizajes/{id}", resultadoAprendizaje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResultadoAprendizaje> resultadoAprendizajeList = resultadoAprendizajeRepository.findAll();
        assertThat(resultadoAprendizajeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadoAprendizaje.class);
        ResultadoAprendizaje resultadoAprendizaje1 = new ResultadoAprendizaje();
        resultadoAprendizaje1.setId(1L);
        ResultadoAprendizaje resultadoAprendizaje2 = new ResultadoAprendizaje();
        resultadoAprendizaje2.setId(resultadoAprendizaje1.getId());
        assertThat(resultadoAprendizaje1).isEqualTo(resultadoAprendizaje2);
        resultadoAprendizaje2.setId(2L);
        assertThat(resultadoAprendizaje1).isNotEqualTo(resultadoAprendizaje2);
        resultadoAprendizaje1.setId(null);
        assertThat(resultadoAprendizaje1).isNotEqualTo(resultadoAprendizaje2);
    }
}
