package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.DiaJornada;
import co.edu.sena.ghostceet.domain.JornadaInstructor;
import co.edu.sena.ghostceet.repository.DiaJornadaRepository;
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
 * Test class for the DiaJornadaResource REST controller.
 *
 * @see DiaJornadaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class DiaJornadaResourceIntTest {

    private static final Integer DEFAULT_HORA_INICIO = 1;
    private static final Integer UPDATED_HORA_INICIO = 2;

    private static final Integer DEFAULT_HORA_FIN = 1;
    private static final Integer UPDATED_HORA_FIN = 2;

    @Autowired
    private DiaJornadaRepository diaJornadaRepository;

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

    private MockMvc restDiaJornadaMockMvc;

    private DiaJornada diaJornada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiaJornadaResource diaJornadaResource = new DiaJornadaResource(diaJornadaRepository);
        this.restDiaJornadaMockMvc = MockMvcBuilders.standaloneSetup(diaJornadaResource)
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
    public static DiaJornada createEntity(EntityManager em) {
        DiaJornada diaJornada = new DiaJornada()
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFin(DEFAULT_HORA_FIN);
        // Add required entity
        JornadaInstructor jornadaInstructor = JornadaInstructorResourceIntTest.createEntity(em);
        em.persist(jornadaInstructor);
        em.flush();
        diaJornada.setJornadaInstructor(jornadaInstructor);
        return diaJornada;
    }

    @Before
    public void initTest() {
        diaJornada = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiaJornada() throws Exception {
        int databaseSizeBeforeCreate = diaJornadaRepository.findAll().size();

        // Create the DiaJornada
        restDiaJornadaMockMvc.perform(post("/api/dia-jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaJornada)))
            .andExpect(status().isCreated());

        // Validate the DiaJornada in the database
        List<DiaJornada> diaJornadaList = diaJornadaRepository.findAll();
        assertThat(diaJornadaList).hasSize(databaseSizeBeforeCreate + 1);
        DiaJornada testDiaJornada = diaJornadaList.get(diaJornadaList.size() - 1);
        assertThat(testDiaJornada.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testDiaJornada.getHoraFin()).isEqualTo(DEFAULT_HORA_FIN);
    }

    @Test
    @Transactional
    public void createDiaJornadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diaJornadaRepository.findAll().size();

        // Create the DiaJornada with an existing ID
        diaJornada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaJornadaMockMvc.perform(post("/api/dia-jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaJornada)))
            .andExpect(status().isBadRequest());

        // Validate the DiaJornada in the database
        List<DiaJornada> diaJornadaList = diaJornadaRepository.findAll();
        assertThat(diaJornadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = diaJornadaRepository.findAll().size();
        // set the field null
        diaJornada.setHoraInicio(null);

        // Create the DiaJornada, which fails.

        restDiaJornadaMockMvc.perform(post("/api/dia-jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaJornada)))
            .andExpect(status().isBadRequest());

        List<DiaJornada> diaJornadaList = diaJornadaRepository.findAll();
        assertThat(diaJornadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = diaJornadaRepository.findAll().size();
        // set the field null
        diaJornada.setHoraFin(null);

        // Create the DiaJornada, which fails.

        restDiaJornadaMockMvc.perform(post("/api/dia-jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaJornada)))
            .andExpect(status().isBadRequest());

        List<DiaJornada> diaJornadaList = diaJornadaRepository.findAll();
        assertThat(diaJornadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiaJornadas() throws Exception {
        // Initialize the database
        diaJornadaRepository.saveAndFlush(diaJornada);

        // Get all the diaJornadaList
        restDiaJornadaMockMvc.perform(get("/api/dia-jornadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaJornada.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO)))
            .andExpect(jsonPath("$.[*].horaFin").value(hasItem(DEFAULT_HORA_FIN)));
    }
    
    @Test
    @Transactional
    public void getDiaJornada() throws Exception {
        // Initialize the database
        diaJornadaRepository.saveAndFlush(diaJornada);

        // Get the diaJornada
        restDiaJornadaMockMvc.perform(get("/api/dia-jornadas/{id}", diaJornada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diaJornada.getId().intValue()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO))
            .andExpect(jsonPath("$.horaFin").value(DEFAULT_HORA_FIN));
    }

    @Test
    @Transactional
    public void getNonExistingDiaJornada() throws Exception {
        // Get the diaJornada
        restDiaJornadaMockMvc.perform(get("/api/dia-jornadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiaJornada() throws Exception {
        // Initialize the database
        diaJornadaRepository.saveAndFlush(diaJornada);

        int databaseSizeBeforeUpdate = diaJornadaRepository.findAll().size();

        // Update the diaJornada
        DiaJornada updatedDiaJornada = diaJornadaRepository.findById(diaJornada.getId()).get();
        // Disconnect from session so that the updates on updatedDiaJornada are not directly saved in db
        em.detach(updatedDiaJornada);
        updatedDiaJornada
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFin(UPDATED_HORA_FIN);

        restDiaJornadaMockMvc.perform(put("/api/dia-jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiaJornada)))
            .andExpect(status().isOk());

        // Validate the DiaJornada in the database
        List<DiaJornada> diaJornadaList = diaJornadaRepository.findAll();
        assertThat(diaJornadaList).hasSize(databaseSizeBeforeUpdate);
        DiaJornada testDiaJornada = diaJornadaList.get(diaJornadaList.size() - 1);
        assertThat(testDiaJornada.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testDiaJornada.getHoraFin()).isEqualTo(UPDATED_HORA_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingDiaJornada() throws Exception {
        int databaseSizeBeforeUpdate = diaJornadaRepository.findAll().size();

        // Create the DiaJornada

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaJornadaMockMvc.perform(put("/api/dia-jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaJornada)))
            .andExpect(status().isBadRequest());

        // Validate the DiaJornada in the database
        List<DiaJornada> diaJornadaList = diaJornadaRepository.findAll();
        assertThat(diaJornadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiaJornada() throws Exception {
        // Initialize the database
        diaJornadaRepository.saveAndFlush(diaJornada);

        int databaseSizeBeforeDelete = diaJornadaRepository.findAll().size();

        // Delete the diaJornada
        restDiaJornadaMockMvc.perform(delete("/api/dia-jornadas/{id}", diaJornada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DiaJornada> diaJornadaList = diaJornadaRepository.findAll();
        assertThat(diaJornadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaJornada.class);
        DiaJornada diaJornada1 = new DiaJornada();
        diaJornada1.setId(1L);
        DiaJornada diaJornada2 = new DiaJornada();
        diaJornada2.setId(diaJornada1.getId());
        assertThat(diaJornada1).isEqualTo(diaJornada2);
        diaJornada2.setId(2L);
        assertThat(diaJornada1).isNotEqualTo(diaJornada2);
        diaJornada1.setId(null);
        assertThat(diaJornada1).isNotEqualTo(diaJornada2);
    }
}
