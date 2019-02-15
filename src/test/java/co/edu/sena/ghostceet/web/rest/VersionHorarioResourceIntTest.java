package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.VersionHorario;
import co.edu.sena.ghostceet.domain.TrimestreVigente;
import co.edu.sena.ghostceet.repository.VersionHorarioRepository;
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
 * Test class for the VersionHorarioResource REST controller.
 *
 * @see VersionHorarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class VersionHorarioResourceIntTest {

    private static final Integer DEFAULT_NUMERO_VERSION = 1;
    private static final Integer UPDATED_NUMERO_VERSION = 2;

    private static final Estado DEFAULT_ESTADO = Estado.Habilitado;
    private static final Estado UPDATED_ESTADO = Estado.Inhabilitado;

    private static final Integer DEFAULT_TRIMESTRE_VIGENTE_ID = 1;
    private static final Integer UPDATED_TRIMESTRE_VIGENTE_ID = 2;

    @Autowired
    private VersionHorarioRepository versionHorarioRepository;

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

    private MockMvc restVersionHorarioMockMvc;

    private VersionHorario versionHorario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VersionHorarioResource versionHorarioResource = new VersionHorarioResource(versionHorarioRepository);
        this.restVersionHorarioMockMvc = MockMvcBuilders.standaloneSetup(versionHorarioResource)
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
    public static VersionHorario createEntity(EntityManager em) {
        VersionHorario versionHorario = new VersionHorario()
            .numeroVersion(DEFAULT_NUMERO_VERSION)
            .estado(DEFAULT_ESTADO)
            .trimestreVigenteId(DEFAULT_TRIMESTRE_VIGENTE_ID);
        // Add required entity
        TrimestreVigente trimestreVigente = TrimestreVigenteResourceIntTest.createEntity(em);
        em.persist(trimestreVigente);
        em.flush();
        versionHorario.setVersionTrimestre(trimestreVigente);
        return versionHorario;
    }

    @Before
    public void initTest() {
        versionHorario = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersionHorario() throws Exception {
        int databaseSizeBeforeCreate = versionHorarioRepository.findAll().size();

        // Create the VersionHorario
        restVersionHorarioMockMvc.perform(post("/api/version-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionHorario)))
            .andExpect(status().isCreated());

        // Validate the VersionHorario in the database
        List<VersionHorario> versionHorarioList = versionHorarioRepository.findAll();
        assertThat(versionHorarioList).hasSize(databaseSizeBeforeCreate + 1);
        VersionHorario testVersionHorario = versionHorarioList.get(versionHorarioList.size() - 1);
        assertThat(testVersionHorario.getNumeroVersion()).isEqualTo(DEFAULT_NUMERO_VERSION);
        assertThat(testVersionHorario.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testVersionHorario.getTrimestreVigenteId()).isEqualTo(DEFAULT_TRIMESTRE_VIGENTE_ID);
    }

    @Test
    @Transactional
    public void createVersionHorarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = versionHorarioRepository.findAll().size();

        // Create the VersionHorario with an existing ID
        versionHorario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersionHorarioMockMvc.perform(post("/api/version-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionHorario)))
            .andExpect(status().isBadRequest());

        // Validate the VersionHorario in the database
        List<VersionHorario> versionHorarioList = versionHorarioRepository.findAll();
        assertThat(versionHorarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionHorarioRepository.findAll().size();
        // set the field null
        versionHorario.setNumeroVersion(null);

        // Create the VersionHorario, which fails.

        restVersionHorarioMockMvc.perform(post("/api/version-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionHorario)))
            .andExpect(status().isBadRequest());

        List<VersionHorario> versionHorarioList = versionHorarioRepository.findAll();
        assertThat(versionHorarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionHorarioRepository.findAll().size();
        // set the field null
        versionHorario.setEstado(null);

        // Create the VersionHorario, which fails.

        restVersionHorarioMockMvc.perform(post("/api/version-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionHorario)))
            .andExpect(status().isBadRequest());

        List<VersionHorario> versionHorarioList = versionHorarioRepository.findAll();
        assertThat(versionHorarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrimestreVigenteIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionHorarioRepository.findAll().size();
        // set the field null
        versionHorario.setTrimestreVigenteId(null);

        // Create the VersionHorario, which fails.

        restVersionHorarioMockMvc.perform(post("/api/version-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionHorario)))
            .andExpect(status().isBadRequest());

        List<VersionHorario> versionHorarioList = versionHorarioRepository.findAll();
        assertThat(versionHorarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVersionHorarios() throws Exception {
        // Initialize the database
        versionHorarioRepository.saveAndFlush(versionHorario);

        // Get all the versionHorarioList
        restVersionHorarioMockMvc.perform(get("/api/version-horarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionHorario.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroVersion").value(hasItem(DEFAULT_NUMERO_VERSION)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].trimestreVigenteId").value(hasItem(DEFAULT_TRIMESTRE_VIGENTE_ID)));
    }
    
    @Test
    @Transactional
    public void getVersionHorario() throws Exception {
        // Initialize the database
        versionHorarioRepository.saveAndFlush(versionHorario);

        // Get the versionHorario
        restVersionHorarioMockMvc.perform(get("/api/version-horarios/{id}", versionHorario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(versionHorario.getId().intValue()))
            .andExpect(jsonPath("$.numeroVersion").value(DEFAULT_NUMERO_VERSION))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.trimestreVigenteId").value(DEFAULT_TRIMESTRE_VIGENTE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingVersionHorario() throws Exception {
        // Get the versionHorario
        restVersionHorarioMockMvc.perform(get("/api/version-horarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersionHorario() throws Exception {
        // Initialize the database
        versionHorarioRepository.saveAndFlush(versionHorario);

        int databaseSizeBeforeUpdate = versionHorarioRepository.findAll().size();

        // Update the versionHorario
        VersionHorario updatedVersionHorario = versionHorarioRepository.findById(versionHorario.getId()).get();
        // Disconnect from session so that the updates on updatedVersionHorario are not directly saved in db
        em.detach(updatedVersionHorario);
        updatedVersionHorario
            .numeroVersion(UPDATED_NUMERO_VERSION)
            .estado(UPDATED_ESTADO)
            .trimestreVigenteId(UPDATED_TRIMESTRE_VIGENTE_ID);

        restVersionHorarioMockMvc.perform(put("/api/version-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVersionHorario)))
            .andExpect(status().isOk());

        // Validate the VersionHorario in the database
        List<VersionHorario> versionHorarioList = versionHorarioRepository.findAll();
        assertThat(versionHorarioList).hasSize(databaseSizeBeforeUpdate);
        VersionHorario testVersionHorario = versionHorarioList.get(versionHorarioList.size() - 1);
        assertThat(testVersionHorario.getNumeroVersion()).isEqualTo(UPDATED_NUMERO_VERSION);
        assertThat(testVersionHorario.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testVersionHorario.getTrimestreVigenteId()).isEqualTo(UPDATED_TRIMESTRE_VIGENTE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingVersionHorario() throws Exception {
        int databaseSizeBeforeUpdate = versionHorarioRepository.findAll().size();

        // Create the VersionHorario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionHorarioMockMvc.perform(put("/api/version-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionHorario)))
            .andExpect(status().isBadRequest());

        // Validate the VersionHorario in the database
        List<VersionHorario> versionHorarioList = versionHorarioRepository.findAll();
        assertThat(versionHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVersionHorario() throws Exception {
        // Initialize the database
        versionHorarioRepository.saveAndFlush(versionHorario);

        int databaseSizeBeforeDelete = versionHorarioRepository.findAll().size();

        // Delete the versionHorario
        restVersionHorarioMockMvc.perform(delete("/api/version-horarios/{id}", versionHorario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VersionHorario> versionHorarioList = versionHorarioRepository.findAll();
        assertThat(versionHorarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VersionHorario.class);
        VersionHorario versionHorario1 = new VersionHorario();
        versionHorario1.setId(1L);
        VersionHorario versionHorario2 = new VersionHorario();
        versionHorario2.setId(versionHorario1.getId());
        assertThat(versionHorario1).isEqualTo(versionHorario2);
        versionHorario2.setId(2L);
        assertThat(versionHorario1).isNotEqualTo(versionHorario2);
        versionHorario1.setId(null);
        assertThat(versionHorario1).isNotEqualTo(versionHorario2);
    }
}
