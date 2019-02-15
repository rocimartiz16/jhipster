package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.Ambiente;
import co.edu.sena.ghostceet.domain.TipoAmbiente;
import co.edu.sena.ghostceet.domain.Sede;
import co.edu.sena.ghostceet.repository.AmbienteRepository;
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
 * Test class for the AmbienteResource REST controller.
 *
 * @see AmbienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class AmbienteResourceIntTest {

    private static final String DEFAULT_NUMERO_AMBIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_AMBIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.Habilitado;
    private static final Estado UPDATED_ESTADO = Estado.Inhabilitado;

    private static final String DEFAULT_LIMITACION = "AAAAAAAAAA";
    private static final String UPDATED_LIMITACION = "BBBBBBBBBB";

    @Autowired
    private AmbienteRepository ambienteRepository;

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

    private MockMvc restAmbienteMockMvc;

    private Ambiente ambiente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmbienteResource ambienteResource = new AmbienteResource(ambienteRepository);
        this.restAmbienteMockMvc = MockMvcBuilders.standaloneSetup(ambienteResource)
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
    public static Ambiente createEntity(EntityManager em) {
        Ambiente ambiente = new Ambiente()
            .numeroAmbiente(DEFAULT_NUMERO_AMBIENTE)
            .descripcion(DEFAULT_DESCRIPCION)
            .estado(DEFAULT_ESTADO)
            .limitacion(DEFAULT_LIMITACION);
        // Add required entity
        TipoAmbiente tipoAmbiente = TipoAmbienteResourceIntTest.createEntity(em);
        em.persist(tipoAmbiente);
        em.flush();
        ambiente.setTipoAmbiente(tipoAmbiente);
        // Add required entity
        Sede sede = SedeResourceIntTest.createEntity(em);
        em.persist(sede);
        em.flush();
        ambiente.setSede(sede);
        return ambiente;
    }

    @Before
    public void initTest() {
        ambiente = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmbiente() throws Exception {
        int databaseSizeBeforeCreate = ambienteRepository.findAll().size();

        // Create the Ambiente
        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambiente)))
            .andExpect(status().isCreated());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeCreate + 1);
        Ambiente testAmbiente = ambienteList.get(ambienteList.size() - 1);
        assertThat(testAmbiente.getNumeroAmbiente()).isEqualTo(DEFAULT_NUMERO_AMBIENTE);
        assertThat(testAmbiente.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAmbiente.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAmbiente.getLimitacion()).isEqualTo(DEFAULT_LIMITACION);
    }

    @Test
    @Transactional
    public void createAmbienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ambienteRepository.findAll().size();

        // Create the Ambiente with an existing ID
        ambiente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambiente)))
            .andExpect(status().isBadRequest());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroAmbienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = ambienteRepository.findAll().size();
        // set the field null
        ambiente.setNumeroAmbiente(null);

        // Create the Ambiente, which fails.

        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambiente)))
            .andExpect(status().isBadRequest());

        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ambienteRepository.findAll().size();
        // set the field null
        ambiente.setDescripcion(null);

        // Create the Ambiente, which fails.

        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambiente)))
            .andExpect(status().isBadRequest());

        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ambienteRepository.findAll().size();
        // set the field null
        ambiente.setEstado(null);

        // Create the Ambiente, which fails.

        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambiente)))
            .andExpect(status().isBadRequest());

        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLimitacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ambienteRepository.findAll().size();
        // set the field null
        ambiente.setLimitacion(null);

        // Create the Ambiente, which fails.

        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambiente)))
            .andExpect(status().isBadRequest());

        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmbientes() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList
        restAmbienteMockMvc.perform(get("/api/ambientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ambiente.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroAmbiente").value(hasItem(DEFAULT_NUMERO_AMBIENTE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].limitacion").value(hasItem(DEFAULT_LIMITACION.toString())));
    }
    
    @Test
    @Transactional
    public void getAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get the ambiente
        restAmbienteMockMvc.perform(get("/api/ambientes/{id}", ambiente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ambiente.getId().intValue()))
            .andExpect(jsonPath("$.numeroAmbiente").value(DEFAULT_NUMERO_AMBIENTE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.limitacion").value(DEFAULT_LIMITACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmbiente() throws Exception {
        // Get the ambiente
        restAmbienteMockMvc.perform(get("/api/ambientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();

        // Update the ambiente
        Ambiente updatedAmbiente = ambienteRepository.findById(ambiente.getId()).get();
        // Disconnect from session so that the updates on updatedAmbiente are not directly saved in db
        em.detach(updatedAmbiente);
        updatedAmbiente
            .numeroAmbiente(UPDATED_NUMERO_AMBIENTE)
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO)
            .limitacion(UPDATED_LIMITACION);

        restAmbienteMockMvc.perform(put("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmbiente)))
            .andExpect(status().isOk());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
        Ambiente testAmbiente = ambienteList.get(ambienteList.size() - 1);
        assertThat(testAmbiente.getNumeroAmbiente()).isEqualTo(UPDATED_NUMERO_AMBIENTE);
        assertThat(testAmbiente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAmbiente.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAmbiente.getLimitacion()).isEqualTo(UPDATED_LIMITACION);
    }

    @Test
    @Transactional
    public void updateNonExistingAmbiente() throws Exception {
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();

        // Create the Ambiente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmbienteMockMvc.perform(put("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambiente)))
            .andExpect(status().isBadRequest());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        int databaseSizeBeforeDelete = ambienteRepository.findAll().size();

        // Delete the ambiente
        restAmbienteMockMvc.perform(delete("/api/ambientes/{id}", ambiente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ambiente.class);
        Ambiente ambiente1 = new Ambiente();
        ambiente1.setId(1L);
        Ambiente ambiente2 = new Ambiente();
        ambiente2.setId(ambiente1.getId());
        assertThat(ambiente1).isEqualTo(ambiente2);
        ambiente2.setId(2L);
        assertThat(ambiente1).isNotEqualTo(ambiente2);
        ambiente1.setId(null);
        assertThat(ambiente1).isNotEqualTo(ambiente2);
    }
}
