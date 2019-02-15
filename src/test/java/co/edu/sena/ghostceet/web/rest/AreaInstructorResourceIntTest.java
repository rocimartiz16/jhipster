package co.edu.sena.ghostceet.web.rest;

import co.edu.sena.ghostceet.GhostceetApp;

import co.edu.sena.ghostceet.domain.AreaInstructor;
import co.edu.sena.ghostceet.domain.Area;
import co.edu.sena.ghostceet.domain.Instructor;
import co.edu.sena.ghostceet.repository.AreaInstructorRepository;
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
 * Test class for the AreaInstructorResource REST controller.
 *
 * @see AreaInstructorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GhostceetApp.class)
public class AreaInstructorResourceIntTest {

    private static final Estado DEFAULT_ESTADO = Estado.Habilitado;
    private static final Estado UPDATED_ESTADO = Estado.Inhabilitado;

    @Autowired
    private AreaInstructorRepository areaInstructorRepository;

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

    private MockMvc restAreaInstructorMockMvc;

    private AreaInstructor areaInstructor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AreaInstructorResource areaInstructorResource = new AreaInstructorResource(areaInstructorRepository);
        this.restAreaInstructorMockMvc = MockMvcBuilders.standaloneSetup(areaInstructorResource)
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
    public static AreaInstructor createEntity(EntityManager em) {
        AreaInstructor areaInstructor = new AreaInstructor()
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Area area = AreaResourceIntTest.createEntity(em);
        em.persist(area);
        em.flush();
        areaInstructor.setArea(area);
        // Add required entity
        Instructor instructor = InstructorResourceIntTest.createEntity(em);
        em.persist(instructor);
        em.flush();
        areaInstructor.setInstructor(instructor);
        return areaInstructor;
    }

    @Before
    public void initTest() {
        areaInstructor = createEntity(em);
    }

    @Test
    @Transactional
    public void createAreaInstructor() throws Exception {
        int databaseSizeBeforeCreate = areaInstructorRepository.findAll().size();

        // Create the AreaInstructor
        restAreaInstructorMockMvc.perform(post("/api/area-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaInstructor)))
            .andExpect(status().isCreated());

        // Validate the AreaInstructor in the database
        List<AreaInstructor> areaInstructorList = areaInstructorRepository.findAll();
        assertThat(areaInstructorList).hasSize(databaseSizeBeforeCreate + 1);
        AreaInstructor testAreaInstructor = areaInstructorList.get(areaInstructorList.size() - 1);
        assertThat(testAreaInstructor.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createAreaInstructorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaInstructorRepository.findAll().size();

        // Create the AreaInstructor with an existing ID
        areaInstructor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaInstructorMockMvc.perform(post("/api/area-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaInstructor)))
            .andExpect(status().isBadRequest());

        // Validate the AreaInstructor in the database
        List<AreaInstructor> areaInstructorList = areaInstructorRepository.findAll();
        assertThat(areaInstructorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaInstructorRepository.findAll().size();
        // set the field null
        areaInstructor.setEstado(null);

        // Create the AreaInstructor, which fails.

        restAreaInstructorMockMvc.perform(post("/api/area-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaInstructor)))
            .andExpect(status().isBadRequest());

        List<AreaInstructor> areaInstructorList = areaInstructorRepository.findAll();
        assertThat(areaInstructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAreaInstructors() throws Exception {
        // Initialize the database
        areaInstructorRepository.saveAndFlush(areaInstructor);

        // Get all the areaInstructorList
        restAreaInstructorMockMvc.perform(get("/api/area-instructors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaInstructor.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getAreaInstructor() throws Exception {
        // Initialize the database
        areaInstructorRepository.saveAndFlush(areaInstructor);

        // Get the areaInstructor
        restAreaInstructorMockMvc.perform(get("/api/area-instructors/{id}", areaInstructor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(areaInstructor.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAreaInstructor() throws Exception {
        // Get the areaInstructor
        restAreaInstructorMockMvc.perform(get("/api/area-instructors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAreaInstructor() throws Exception {
        // Initialize the database
        areaInstructorRepository.saveAndFlush(areaInstructor);

        int databaseSizeBeforeUpdate = areaInstructorRepository.findAll().size();

        // Update the areaInstructor
        AreaInstructor updatedAreaInstructor = areaInstructorRepository.findById(areaInstructor.getId()).get();
        // Disconnect from session so that the updates on updatedAreaInstructor are not directly saved in db
        em.detach(updatedAreaInstructor);
        updatedAreaInstructor
            .estado(UPDATED_ESTADO);

        restAreaInstructorMockMvc.perform(put("/api/area-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAreaInstructor)))
            .andExpect(status().isOk());

        // Validate the AreaInstructor in the database
        List<AreaInstructor> areaInstructorList = areaInstructorRepository.findAll();
        assertThat(areaInstructorList).hasSize(databaseSizeBeforeUpdate);
        AreaInstructor testAreaInstructor = areaInstructorList.get(areaInstructorList.size() - 1);
        assertThat(testAreaInstructor.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingAreaInstructor() throws Exception {
        int databaseSizeBeforeUpdate = areaInstructorRepository.findAll().size();

        // Create the AreaInstructor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaInstructorMockMvc.perform(put("/api/area-instructors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaInstructor)))
            .andExpect(status().isBadRequest());

        // Validate the AreaInstructor in the database
        List<AreaInstructor> areaInstructorList = areaInstructorRepository.findAll();
        assertThat(areaInstructorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAreaInstructor() throws Exception {
        // Initialize the database
        areaInstructorRepository.saveAndFlush(areaInstructor);

        int databaseSizeBeforeDelete = areaInstructorRepository.findAll().size();

        // Delete the areaInstructor
        restAreaInstructorMockMvc.perform(delete("/api/area-instructors/{id}", areaInstructor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AreaInstructor> areaInstructorList = areaInstructorRepository.findAll();
        assertThat(areaInstructorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaInstructor.class);
        AreaInstructor areaInstructor1 = new AreaInstructor();
        areaInstructor1.setId(1L);
        AreaInstructor areaInstructor2 = new AreaInstructor();
        areaInstructor2.setId(areaInstructor1.getId());
        assertThat(areaInstructor1).isEqualTo(areaInstructor2);
        areaInstructor2.setId(2L);
        assertThat(areaInstructor1).isNotEqualTo(areaInstructor2);
        areaInstructor1.setId(null);
        assertThat(areaInstructor1).isNotEqualTo(areaInstructor2);
    }
}
