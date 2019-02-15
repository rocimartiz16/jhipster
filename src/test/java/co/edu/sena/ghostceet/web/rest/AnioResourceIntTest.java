package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.Anio;
import co.edu.sena.ghostceet.repository.AnioRepository;
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
 * Test class for the AnioResource REST controller.
 *
 * @see AnioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class AnioResourceIntTest {

    private static final Integer DEFAULT_NUMERO_ANIO = 1;
    private static final Integer UPDATED_NUMERO_ANIO = 2;

    private static final Estado DEFAULT_ESTADO = Estado.Habilitado;
    private static final Estado UPDATED_ESTADO = Estado.Inhabilitado;

    @Autowired
    private AnioRepository anioRepository;

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

    private MockMvc restAnioMockMvc;

    private Anio anio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnioResource anioResource = new AnioResource(anioRepository);
        this.restAnioMockMvc = MockMvcBuilders.standaloneSetup(anioResource)
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
    public static Anio createEntity(EntityManager em) {
        Anio anio = new Anio()
            .numeroAnio(DEFAULT_NUMERO_ANIO)
            .estado(DEFAULT_ESTADO);
        return anio;
    }

    @Before
    public void initTest() {
        anio = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnio() throws Exception {
        int databaseSizeBeforeCreate = anioRepository.findAll().size();

        // Create the Anio
        restAnioMockMvc.perform(post("/api/anios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anio)))
            .andExpect(status().isCreated());

        // Validate the Anio in the database
        List<Anio> anioList = anioRepository.findAll();
        assertThat(anioList).hasSize(databaseSizeBeforeCreate + 1);
        Anio testAnio = anioList.get(anioList.size() - 1);
        assertThat(testAnio.getNumeroAnio()).isEqualTo(DEFAULT_NUMERO_ANIO);
        assertThat(testAnio.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createAnioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anioRepository.findAll().size();

        // Create the Anio with an existing ID
        anio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnioMockMvc.perform(post("/api/anios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anio)))
            .andExpect(status().isBadRequest());

        // Validate the Anio in the database
        List<Anio> anioList = anioRepository.findAll();
        assertThat(anioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = anioRepository.findAll().size();
        // set the field null
        anio.setEstado(null);

        // Create the Anio, which fails.

        restAnioMockMvc.perform(post("/api/anios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anio)))
            .andExpect(status().isBadRequest());

        List<Anio> anioList = anioRepository.findAll();
        assertThat(anioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnios() throws Exception {
        // Initialize the database
        anioRepository.saveAndFlush(anio);

        // Get all the anioList
        restAnioMockMvc.perform(get("/api/anios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anio.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroAnio").value(hasItem(DEFAULT_NUMERO_ANIO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getAnio() throws Exception {
        // Initialize the database
        anioRepository.saveAndFlush(anio);

        // Get the anio
        restAnioMockMvc.perform(get("/api/anios/{id}", anio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anio.getId().intValue()))
            .andExpect(jsonPath("$.numeroAnio").value(DEFAULT_NUMERO_ANIO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnio() throws Exception {
        // Get the anio
        restAnioMockMvc.perform(get("/api/anios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnio() throws Exception {
        // Initialize the database
        anioRepository.saveAndFlush(anio);

        int databaseSizeBeforeUpdate = anioRepository.findAll().size();

        // Update the anio
        Anio updatedAnio = anioRepository.findById(anio.getId()).get();
        // Disconnect from session so that the updates on updatedAnio are not directly saved in db
        em.detach(updatedAnio);
        updatedAnio
            .numeroAnio(UPDATED_NUMERO_ANIO)
            .estado(UPDATED_ESTADO);

        restAnioMockMvc.perform(put("/api/anios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnio)))
            .andExpect(status().isOk());

        // Validate the Anio in the database
        List<Anio> anioList = anioRepository.findAll();
        assertThat(anioList).hasSize(databaseSizeBeforeUpdate);
        Anio testAnio = anioList.get(anioList.size() - 1);
        assertThat(testAnio.getNumeroAnio()).isEqualTo(UPDATED_NUMERO_ANIO);
        assertThat(testAnio.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnio() throws Exception {
        int databaseSizeBeforeUpdate = anioRepository.findAll().size();

        // Create the Anio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnioMockMvc.perform(put("/api/anios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anio)))
            .andExpect(status().isBadRequest());

        // Validate the Anio in the database
        List<Anio> anioList = anioRepository.findAll();
        assertThat(anioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnio() throws Exception {
        // Initialize the database
        anioRepository.saveAndFlush(anio);

        int databaseSizeBeforeDelete = anioRepository.findAll().size();

        // Delete the anio
        restAnioMockMvc.perform(delete("/api/anios/{id}", anio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Anio> anioList = anioRepository.findAll();
        assertThat(anioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anio.class);
        Anio anio1 = new Anio();
        anio1.setId(1L);
        Anio anio2 = new Anio();
        anio2.setId(anio1.getId());
        assertThat(anio1).isEqualTo(anio2);
        anio2.setId(2L);
        assertThat(anio1).isNotEqualTo(anio2);
        anio1.setId(null);
        assertThat(anio1).isNotEqualTo(anio2);
    }
}
