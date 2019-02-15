package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.FaseProyecto;
import co.edu.sena.ghostceet.domain.Proyecto;
import co.edu.sena.ghostceet.repository.FaseProyectoRepository;
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
 * Test class for the FaseProyectoResource REST controller.
 *
 * @see FaseProyectoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class FaseProyectoResourceIntTest {

    private static final String DEFAULT_NOMBRE_FASE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_FASE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_FASE = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_FASE = "BBBBBBBBBB";

    @Autowired
    private FaseProyectoRepository faseProyectoRepository;

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

    private MockMvc restFaseProyectoMockMvc;

    private FaseProyecto faseProyecto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaseProyectoResource faseProyectoResource = new FaseProyectoResource(faseProyectoRepository);
        this.restFaseProyectoMockMvc = MockMvcBuilders.standaloneSetup(faseProyectoResource)
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
    public static FaseProyecto createEntity(EntityManager em) {
        FaseProyecto faseProyecto = new FaseProyecto()
            .nombreFase(DEFAULT_NOMBRE_FASE)
            .estadoFase(DEFAULT_ESTADO_FASE);
        // Add required entity
        Proyecto proyecto = ProyectoResourceIntTest.createEntity(em);
        em.persist(proyecto);
        em.flush();
        faseProyecto.setProyecto(proyecto);
        return faseProyecto;
    }

    @Before
    public void initTest() {
        faseProyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaseProyecto() throws Exception {
        int databaseSizeBeforeCreate = faseProyectoRepository.findAll().size();

        // Create the FaseProyecto
        restFaseProyectoMockMvc.perform(post("/api/fase-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseProyecto)))
            .andExpect(status().isCreated());

        // Validate the FaseProyecto in the database
        List<FaseProyecto> faseProyectoList = faseProyectoRepository.findAll();
        assertThat(faseProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        FaseProyecto testFaseProyecto = faseProyectoList.get(faseProyectoList.size() - 1);
        assertThat(testFaseProyecto.getNombreFase()).isEqualTo(DEFAULT_NOMBRE_FASE);
        assertThat(testFaseProyecto.getEstadoFase()).isEqualTo(DEFAULT_ESTADO_FASE);
    }

    @Test
    @Transactional
    public void createFaseProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faseProyectoRepository.findAll().size();

        // Create the FaseProyecto with an existing ID
        faseProyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaseProyectoMockMvc.perform(post("/api/fase-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the FaseProyecto in the database
        List<FaseProyecto> faseProyectoList = faseProyectoRepository.findAll();
        assertThat(faseProyectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreFaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = faseProyectoRepository.findAll().size();
        // set the field null
        faseProyecto.setNombreFase(null);

        // Create the FaseProyecto, which fails.

        restFaseProyectoMockMvc.perform(post("/api/fase-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseProyecto)))
            .andExpect(status().isBadRequest());

        List<FaseProyecto> faseProyectoList = faseProyectoRepository.findAll();
        assertThat(faseProyectoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoFaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = faseProyectoRepository.findAll().size();
        // set the field null
        faseProyecto.setEstadoFase(null);

        // Create the FaseProyecto, which fails.

        restFaseProyectoMockMvc.perform(post("/api/fase-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseProyecto)))
            .andExpect(status().isBadRequest());

        List<FaseProyecto> faseProyectoList = faseProyectoRepository.findAll();
        assertThat(faseProyectoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaseProyectos() throws Exception {
        // Initialize the database
        faseProyectoRepository.saveAndFlush(faseProyecto);

        // Get all the faseProyectoList
        restFaseProyectoMockMvc.perform(get("/api/fase-proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faseProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreFase").value(hasItem(DEFAULT_NOMBRE_FASE.toString())))
            .andExpect(jsonPath("$.[*].estadoFase").value(hasItem(DEFAULT_ESTADO_FASE.toString())));
    }
    
    @Test
    @Transactional
    public void getFaseProyecto() throws Exception {
        // Initialize the database
        faseProyectoRepository.saveAndFlush(faseProyecto);

        // Get the faseProyecto
        restFaseProyectoMockMvc.perform(get("/api/fase-proyectos/{id}", faseProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faseProyecto.getId().intValue()))
            .andExpect(jsonPath("$.nombreFase").value(DEFAULT_NOMBRE_FASE.toString()))
            .andExpect(jsonPath("$.estadoFase").value(DEFAULT_ESTADO_FASE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFaseProyecto() throws Exception {
        // Get the faseProyecto
        restFaseProyectoMockMvc.perform(get("/api/fase-proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaseProyecto() throws Exception {
        // Initialize the database
        faseProyectoRepository.saveAndFlush(faseProyecto);

        int databaseSizeBeforeUpdate = faseProyectoRepository.findAll().size();

        // Update the faseProyecto
        FaseProyecto updatedFaseProyecto = faseProyectoRepository.findById(faseProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedFaseProyecto are not directly saved in db
        em.detach(updatedFaseProyecto);
        updatedFaseProyecto
            .nombreFase(UPDATED_NOMBRE_FASE)
            .estadoFase(UPDATED_ESTADO_FASE);

        restFaseProyectoMockMvc.perform(put("/api/fase-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaseProyecto)))
            .andExpect(status().isOk());

        // Validate the FaseProyecto in the database
        List<FaseProyecto> faseProyectoList = faseProyectoRepository.findAll();
        assertThat(faseProyectoList).hasSize(databaseSizeBeforeUpdate);
        FaseProyecto testFaseProyecto = faseProyectoList.get(faseProyectoList.size() - 1);
        assertThat(testFaseProyecto.getNombreFase()).isEqualTo(UPDATED_NOMBRE_FASE);
        assertThat(testFaseProyecto.getEstadoFase()).isEqualTo(UPDATED_ESTADO_FASE);
    }

    @Test
    @Transactional
    public void updateNonExistingFaseProyecto() throws Exception {
        int databaseSizeBeforeUpdate = faseProyectoRepository.findAll().size();

        // Create the FaseProyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaseProyectoMockMvc.perform(put("/api/fase-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the FaseProyecto in the database
        List<FaseProyecto> faseProyectoList = faseProyectoRepository.findAll();
        assertThat(faseProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaseProyecto() throws Exception {
        // Initialize the database
        faseProyectoRepository.saveAndFlush(faseProyecto);

        int databaseSizeBeforeDelete = faseProyectoRepository.findAll().size();

        // Delete the faseProyecto
        restFaseProyectoMockMvc.perform(delete("/api/fase-proyectos/{id}", faseProyecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FaseProyecto> faseProyectoList = faseProyectoRepository.findAll();
        assertThat(faseProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaseProyecto.class);
        FaseProyecto faseProyecto1 = new FaseProyecto();
        faseProyecto1.setId(1L);
        FaseProyecto faseProyecto2 = new FaseProyecto();
        faseProyecto2.setId(faseProyecto1.getId());
        assertThat(faseProyecto1).isEqualTo(faseProyecto2);
        faseProyecto2.setId(2L);
        assertThat(faseProyecto1).isNotEqualTo(faseProyecto2);
        faseProyecto1.setId(null);
        assertThat(faseProyecto1).isNotEqualTo(faseProyecto2);
    }
}
