package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.Ficha;
import co.edu.sena.ghostceet.domain.Programa;
import co.edu.sena.ghostceet.domain.EstadoFicha;
import co.edu.sena.ghostceet.repository.FichaRepository;
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

import co.edu.sena.ghostceet.domain.enumeration.Estado;
/**
 * Test class for the FichaResource REST controller.
 *
 * @see FichaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class FichaResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_RUTA = "AAAAAAAAAA";
    private static final String UPDATED_RUTA = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.Habilitado;
    private static final Estado UPDATED_ESTADO = Estado.Inhabilitado;

    @Autowired
    private FichaRepository fichaRepository;

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

    private MockMvc restFichaMockMvc;

    private Ficha ficha;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichaResource fichaResource = new FichaResource(fichaRepository);
        this.restFichaMockMvc = MockMvcBuilders.standaloneSetup(fichaResource)
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
    public static Ficha createEntity(EntityManager em) {
        Ficha ficha = new Ficha()
            .numero(DEFAULT_NUMERO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .ruta(DEFAULT_RUTA)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Programa programa = ProgramaResourceIntTest.createEntity(em);
        em.persist(programa);
        em.flush();
        ficha.setPrograma(programa);
        // Add required entity
        EstadoFicha estadoFicha = EstadoFichaResourceIntTest.createEntity(em);
        em.persist(estadoFicha);
        em.flush();
        ficha.setEstadoFicha(estadoFicha);
        return ficha;
    }

    @Before
    public void initTest() {
        ficha = createEntity(em);
    }

    @Test
    @Transactional
    public void createFicha() throws Exception {
        int databaseSizeBeforeCreate = fichaRepository.findAll().size();

        // Create the Ficha
        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficha)))
            .andExpect(status().isCreated());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeCreate + 1);
        Ficha testFicha = fichaList.get(fichaList.size() - 1);
        assertThat(testFicha.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testFicha.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testFicha.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testFicha.getRuta()).isEqualTo(DEFAULT_RUTA);
        assertThat(testFicha.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createFichaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichaRepository.findAll().size();

        // Create the Ficha with an existing ID
        ficha.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficha)))
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaRepository.findAll().size();
        // set the field null
        ficha.setNumero(null);

        // Create the Ficha, which fails.

        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficha)))
            .andExpect(status().isBadRequest());

        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaRepository.findAll().size();
        // set the field null
        ficha.setFechaInicio(null);

        // Create the Ficha, which fails.

        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficha)))
            .andExpect(status().isBadRequest());

        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaRepository.findAll().size();
        // set the field null
        ficha.setFechaFin(null);

        // Create the Ficha, which fails.

        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficha)))
            .andExpect(status().isBadRequest());

        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRutaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaRepository.findAll().size();
        // set the field null
        ficha.setRuta(null);

        // Create the Ficha, which fails.

        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficha)))
            .andExpect(status().isBadRequest());

        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaRepository.findAll().size();
        // set the field null
        ficha.setEstado(null);

        // Create the Ficha, which fails.

        restFichaMockMvc.perform(post("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficha)))
            .andExpect(status().isBadRequest());

        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFichas() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get all the fichaList
        restFichaMockMvc.perform(get("/api/fichas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficha.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(sameInstant(DEFAULT_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(sameInstant(DEFAULT_FECHA_FIN))))
            .andExpect(jsonPath("$.[*].ruta").value(hasItem(DEFAULT_RUTA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        // Get the ficha
        restFichaMockMvc.perform(get("/api/fichas/{id}", ficha.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ficha.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.fechaInicio").value(sameInstant(DEFAULT_FECHA_INICIO)))
            .andExpect(jsonPath("$.fechaFin").value(sameInstant(DEFAULT_FECHA_FIN)))
            .andExpect(jsonPath("$.ruta").value(DEFAULT_RUTA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFicha() throws Exception {
        // Get the ficha
        restFichaMockMvc.perform(get("/api/fichas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();

        // Update the ficha
        Ficha updatedFicha = fichaRepository.findById(ficha.getId()).get();
        // Disconnect from session so that the updates on updatedFicha are not directly saved in db
        em.detach(updatedFicha);
        updatedFicha
            .numero(UPDATED_NUMERO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .ruta(UPDATED_RUTA)
            .estado(UPDATED_ESTADO);

        restFichaMockMvc.perform(put("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFicha)))
            .andExpect(status().isOk());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
        Ficha testFicha = fichaList.get(fichaList.size() - 1);
        assertThat(testFicha.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testFicha.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testFicha.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testFicha.getRuta()).isEqualTo(UPDATED_RUTA);
        assertThat(testFicha.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingFicha() throws Exception {
        int databaseSizeBeforeUpdate = fichaRepository.findAll().size();

        // Create the Ficha

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichaMockMvc.perform(put("/api/fichas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficha)))
            .andExpect(status().isBadRequest());

        // Validate the Ficha in the database
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFicha() throws Exception {
        // Initialize the database
        fichaRepository.saveAndFlush(ficha);

        int databaseSizeBeforeDelete = fichaRepository.findAll().size();

        // Delete the ficha
        restFichaMockMvc.perform(delete("/api/fichas/{id}", ficha.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ficha> fichaList = fichaRepository.findAll();
        assertThat(fichaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ficha.class);
        Ficha ficha1 = new Ficha();
        ficha1.setId(1L);
        Ficha ficha2 = new Ficha();
        ficha2.setId(ficha1.getId());
        assertThat(ficha1).isEqualTo(ficha2);
        ficha2.setId(2L);
        assertThat(ficha1).isNotEqualTo(ficha2);
        ficha1.setId(null);
        assertThat(ficha1).isNotEqualTo(ficha2);
    }
}
