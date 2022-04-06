package com.tfkfan.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tfkfan.IntegrationTest;
import com.tfkfan.domain.Category;
import com.tfkfan.repository.CategoryRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoryResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_CATEGORY_CODE = 1L;
    private static final Long UPDATED_PARENT_CATEGORY_CODE = 2L;
    private static final Long SMALLER_PARENT_CATEGORY_CODE = 1L - 1L;

    private static final Long DEFAULT_PARENT_CATEGORY_ID = 1L;
    private static final Long UPDATED_PARENT_CATEGORY_ID = 2L;
    private static final Long SMALLER_PARENT_CATEGORY_ID = 1L - 1L;

    private static final Boolean DEFAULT_IS_HIDDEN = false;
    private static final Boolean UPDATED_IS_HIDDEN = true;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryMockMvc;

    private Category category;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .parentCategoryCode(DEFAULT_PARENT_CATEGORY_CODE)
            .parent_category_id(DEFAULT_PARENT_CATEGORY_ID)
            .is_hidden(DEFAULT_IS_HIDDEN)
            .creation_date(DEFAULT_CREATION_DATE)
            .modification_date(DEFAULT_MODIFICATION_DATE);
        return category;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createUpdatedEntity(EntityManager em) {
        Category category = new Category()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .parentCategoryCode(UPDATED_PARENT_CATEGORY_CODE)
            .parent_category_id(UPDATED_PARENT_CATEGORY_ID)
            .is_hidden(UPDATED_IS_HIDDEN)
            .creation_date(UPDATED_CREATION_DATE)
            .modification_date(UPDATED_MODIFICATION_DATE);
        return category;
    }

    @BeforeEach
    public void initTest() {
        category = createEntity(em);
    }

    @Test
    @Transactional
    void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
        // Create the Category
        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategory.getParentCategoryId()).isEqualTo(DEFAULT_PARENT_CATEGORY_CODE);
        assertThat(testCategory.getParent_category_id()).isEqualTo(DEFAULT_PARENT_CATEGORY_ID);
        assertThat(testCategory.getIsHidden()).isEqualTo(DEFAULT_IS_HIDDEN);
        assertThat(testCategory.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCategory.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    void createCategoryWithExistingId() throws Exception {
        // Create the Category with an existing ID
        category.setId(1L);

        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setCode(null);

        // Create the Category, which fails.

        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setName(null);

        // Create the Category, which fails.

        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIs_hiddenIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setIsHidden(null);

        // Create the Category, which fails.

        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreation_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setCreationDate(null);

        // Create the Category, which fails.

        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModification_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setModificationDate(null);

        // Create the Category, which fails.

        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].parentCategoryCode").value(hasItem(DEFAULT_PARENT_CATEGORY_CODE.intValue())))
            .andExpect(jsonPath("$.[*].parent_category_id").value(hasItem(DEFAULT_PARENT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].is_hidden").value(hasItem(DEFAULT_IS_HIDDEN.booleanValue())))
            .andExpect(jsonPath("$.[*].creation_date").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modification_date").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }

    @Test
    @Transactional
    void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.parentCategoryCode").value(DEFAULT_PARENT_CATEGORY_CODE.intValue()))
            .andExpect(jsonPath("$.parent_category_id").value(DEFAULT_PARENT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.is_hidden").value(DEFAULT_IS_HIDDEN.booleanValue()))
            .andExpect(jsonPath("$.creation_date").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.modification_date").value(DEFAULT_MODIFICATION_DATE.toString()));
    }

    @Test
    @Transactional
    void getCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        Long id = category.getId();

        defaultCategoryShouldBeFound("id.equals=" + id);
        defaultCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where code equals to DEFAULT_CODE
        defaultCategoryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the categoryList where code equals to UPDATED_CODE
        defaultCategoryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where code not equals to DEFAULT_CODE
        defaultCategoryShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the categoryList where code not equals to UPDATED_CODE
        defaultCategoryShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCategoryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the categoryList where code equals to UPDATED_CODE
        defaultCategoryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where code is not null
        defaultCategoryShouldBeFound("code.specified=true");

        // Get all the categoryList where code is null
        defaultCategoryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where code contains DEFAULT_CODE
        defaultCategoryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the categoryList where code contains UPDATED_CODE
        defaultCategoryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where code does not contain DEFAULT_CODE
        defaultCategoryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the categoryList where code does not contain UPDATED_CODE
        defaultCategoryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where name equals to DEFAULT_NAME
        defaultCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the categoryList where name equals to UPDATED_NAME
        defaultCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where name not equals to DEFAULT_NAME
        defaultCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the categoryList where name not equals to UPDATED_NAME
        defaultCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the categoryList where name equals to UPDATED_NAME
        defaultCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where name is not null
        defaultCategoryShouldBeFound("name.specified=true");

        // Get all the categoryList where name is null
        defaultCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where name contains DEFAULT_NAME
        defaultCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the categoryList where name contains UPDATED_NAME
        defaultCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where name does not contain DEFAULT_NAME
        defaultCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the categoryList where name does not contain UPDATED_NAME
        defaultCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where description equals to DEFAULT_DESCRIPTION
        defaultCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the categoryList where description equals to UPDATED_DESCRIPTION
        defaultCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where description not equals to DEFAULT_DESCRIPTION
        defaultCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the categoryList where description not equals to UPDATED_DESCRIPTION
        defaultCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the categoryList where description equals to UPDATED_DESCRIPTION
        defaultCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where description is not null
        defaultCategoryShouldBeFound("description.specified=true");

        // Get all the categoryList where description is null
        defaultCategoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where description contains DEFAULT_DESCRIPTION
        defaultCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the categoryList where description contains UPDATED_DESCRIPTION
        defaultCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where description does not contain DEFAULT_DESCRIPTION
        defaultCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the categoryList where description does not contain UPDATED_DESCRIPTION
        defaultCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCategoriesByParentCategoryCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parentCategoryCode equals to DEFAULT_PARENT_CATEGORY_CODE
        defaultCategoryShouldBeFound("parentCategoryCode.equals=" + DEFAULT_PARENT_CATEGORY_CODE);

        // Get all the categoryList where parentCategoryCode equals to UPDATED_PARENT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("parentCategoryCode.equals=" + UPDATED_PARENT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByParentCategoryCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parentCategoryCode not equals to DEFAULT_PARENT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("parentCategoryCode.notEquals=" + DEFAULT_PARENT_CATEGORY_CODE);

        // Get all the categoryList where parentCategoryCode not equals to UPDATED_PARENT_CATEGORY_CODE
        defaultCategoryShouldBeFound("parentCategoryCode.notEquals=" + UPDATED_PARENT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByParentCategoryCodeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parentCategoryCode in DEFAULT_PARENT_CATEGORY_CODE or UPDATED_PARENT_CATEGORY_CODE
        defaultCategoryShouldBeFound("parentCategoryCode.in=" + DEFAULT_PARENT_CATEGORY_CODE + "," + UPDATED_PARENT_CATEGORY_CODE);

        // Get all the categoryList where parentCategoryCode equals to UPDATED_PARENT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("parentCategoryCode.in=" + UPDATED_PARENT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByParentCategoryCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parentCategoryCode is not null
        defaultCategoryShouldBeFound("parentCategoryCode.specified=true");

        // Get all the categoryList where parentCategoryCode is null
        defaultCategoryShouldNotBeFound("parentCategoryCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByParentCategoryCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parentCategoryCode is greater than or equal to DEFAULT_PARENT_CATEGORY_CODE
        defaultCategoryShouldBeFound("parentCategoryCode.greaterThanOrEqual=" + DEFAULT_PARENT_CATEGORY_CODE);

        // Get all the categoryList where parentCategoryCode is greater than or equal to UPDATED_PARENT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("parentCategoryCode.greaterThanOrEqual=" + UPDATED_PARENT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByParentCategoryCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parentCategoryCode is less than or equal to DEFAULT_PARENT_CATEGORY_CODE
        defaultCategoryShouldBeFound("parentCategoryCode.lessThanOrEqual=" + DEFAULT_PARENT_CATEGORY_CODE);

        // Get all the categoryList where parentCategoryCode is less than or equal to SMALLER_PARENT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("parentCategoryCode.lessThanOrEqual=" + SMALLER_PARENT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByParentCategoryCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parentCategoryCode is less than DEFAULT_PARENT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("parentCategoryCode.lessThan=" + DEFAULT_PARENT_CATEGORY_CODE);

        // Get all the categoryList where parentCategoryCode is less than UPDATED_PARENT_CATEGORY_CODE
        defaultCategoryShouldBeFound("parentCategoryCode.lessThan=" + UPDATED_PARENT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByParentCategoryCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parentCategoryCode is greater than DEFAULT_PARENT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("parentCategoryCode.greaterThan=" + DEFAULT_PARENT_CATEGORY_CODE);

        // Get all the categoryList where parentCategoryCode is greater than SMALLER_PARENT_CATEGORY_CODE
        defaultCategoryShouldBeFound("parentCategoryCode.greaterThan=" + SMALLER_PARENT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByParent_category_idIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parent_category_id equals to DEFAULT_PARENT_CATEGORY_ID
        defaultCategoryShouldBeFound("parent_category_id.equals=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoryList where parent_category_id equals to UPDATED_PARENT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("parent_category_id.equals=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllCategoriesByParent_category_idIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parent_category_id not equals to DEFAULT_PARENT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("parent_category_id.notEquals=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoryList where parent_category_id not equals to UPDATED_PARENT_CATEGORY_ID
        defaultCategoryShouldBeFound("parent_category_id.notEquals=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllCategoriesByParent_category_idIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parent_category_id in DEFAULT_PARENT_CATEGORY_ID or UPDATED_PARENT_CATEGORY_ID
        defaultCategoryShouldBeFound("parent_category_id.in=" + DEFAULT_PARENT_CATEGORY_ID + "," + UPDATED_PARENT_CATEGORY_ID);

        // Get all the categoryList where parent_category_id equals to UPDATED_PARENT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("parent_category_id.in=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllCategoriesByParent_category_idIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parent_category_id is not null
        defaultCategoryShouldBeFound("parent_category_id.specified=true");

        // Get all the categoryList where parent_category_id is null
        defaultCategoryShouldNotBeFound("parent_category_id.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByParent_category_idIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parent_category_id is greater than or equal to DEFAULT_PARENT_CATEGORY_ID
        defaultCategoryShouldBeFound("parent_category_id.greaterThanOrEqual=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoryList where parent_category_id is greater than or equal to UPDATED_PARENT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("parent_category_id.greaterThanOrEqual=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllCategoriesByParent_category_idIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parent_category_id is less than or equal to DEFAULT_PARENT_CATEGORY_ID
        defaultCategoryShouldBeFound("parent_category_id.lessThanOrEqual=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoryList where parent_category_id is less than or equal to SMALLER_PARENT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("parent_category_id.lessThanOrEqual=" + SMALLER_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllCategoriesByParent_category_idIsLessThanSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parent_category_id is less than DEFAULT_PARENT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("parent_category_id.lessThan=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoryList where parent_category_id is less than UPDATED_PARENT_CATEGORY_ID
        defaultCategoryShouldBeFound("parent_category_id.lessThan=" + UPDATED_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllCategoriesByParent_category_idIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where parent_category_id is greater than DEFAULT_PARENT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("parent_category_id.greaterThan=" + DEFAULT_PARENT_CATEGORY_ID);

        // Get all the categoryList where parent_category_id is greater than SMALLER_PARENT_CATEGORY_ID
        defaultCategoryShouldBeFound("parent_category_id.greaterThan=" + SMALLER_PARENT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllCategoriesByIs_hiddenIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where is_hidden equals to DEFAULT_IS_HIDDEN
        defaultCategoryShouldBeFound("is_hidden.equals=" + DEFAULT_IS_HIDDEN);

        // Get all the categoryList where is_hidden equals to UPDATED_IS_HIDDEN
        defaultCategoryShouldNotBeFound("is_hidden.equals=" + UPDATED_IS_HIDDEN);
    }

    @Test
    @Transactional
    void getAllCategoriesByIs_hiddenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where is_hidden not equals to DEFAULT_IS_HIDDEN
        defaultCategoryShouldNotBeFound("is_hidden.notEquals=" + DEFAULT_IS_HIDDEN);

        // Get all the categoryList where is_hidden not equals to UPDATED_IS_HIDDEN
        defaultCategoryShouldBeFound("is_hidden.notEquals=" + UPDATED_IS_HIDDEN);
    }

    @Test
    @Transactional
    void getAllCategoriesByIs_hiddenIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where is_hidden in DEFAULT_IS_HIDDEN or UPDATED_IS_HIDDEN
        defaultCategoryShouldBeFound("is_hidden.in=" + DEFAULT_IS_HIDDEN + "," + UPDATED_IS_HIDDEN);

        // Get all the categoryList where is_hidden equals to UPDATED_IS_HIDDEN
        defaultCategoryShouldNotBeFound("is_hidden.in=" + UPDATED_IS_HIDDEN);
    }

    @Test
    @Transactional
    void getAllCategoriesByIs_hiddenIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where is_hidden is not null
        defaultCategoryShouldBeFound("is_hidden.specified=true");

        // Get all the categoryList where is_hidden is null
        defaultCategoryShouldNotBeFound("is_hidden.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCreation_dateIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where creation_date equals to DEFAULT_CREATION_DATE
        defaultCategoryShouldBeFound("creation_date.equals=" + DEFAULT_CREATION_DATE);

        // Get all the categoryList where creation_date equals to UPDATED_CREATION_DATE
        defaultCategoryShouldNotBeFound("creation_date.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCreation_dateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where creation_date not equals to DEFAULT_CREATION_DATE
        defaultCategoryShouldNotBeFound("creation_date.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the categoryList where creation_date not equals to UPDATED_CREATION_DATE
        defaultCategoryShouldBeFound("creation_date.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCreation_dateIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where creation_date in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultCategoryShouldBeFound("creation_date.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the categoryList where creation_date equals to UPDATED_CREATION_DATE
        defaultCategoryShouldNotBeFound("creation_date.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCreation_dateIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where creation_date is not null
        defaultCategoryShouldBeFound("creation_date.specified=true");

        // Get all the categoryList where creation_date is null
        defaultCategoryShouldNotBeFound("creation_date.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByModification_dateIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modification_date equals to DEFAULT_MODIFICATION_DATE
        defaultCategoryShouldBeFound("modification_date.equals=" + DEFAULT_MODIFICATION_DATE);

        // Get all the categoryList where modification_date equals to UPDATED_MODIFICATION_DATE
        defaultCategoryShouldNotBeFound("modification_date.equals=" + UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    void getAllCategoriesByModification_dateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modification_date not equals to DEFAULT_MODIFICATION_DATE
        defaultCategoryShouldNotBeFound("modification_date.notEquals=" + DEFAULT_MODIFICATION_DATE);

        // Get all the categoryList where modification_date not equals to UPDATED_MODIFICATION_DATE
        defaultCategoryShouldBeFound("modification_date.notEquals=" + UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    void getAllCategoriesByModification_dateIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modification_date in DEFAULT_MODIFICATION_DATE or UPDATED_MODIFICATION_DATE
        defaultCategoryShouldBeFound("modification_date.in=" + DEFAULT_MODIFICATION_DATE + "," + UPDATED_MODIFICATION_DATE);

        // Get all the categoryList where modification_date equals to UPDATED_MODIFICATION_DATE
        defaultCategoryShouldNotBeFound("modification_date.in=" + UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    void getAllCategoriesByModification_dateIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modification_date is not null
        defaultCategoryShouldBeFound("modification_date.specified=true");

        // Get all the categoryList where modification_date is null
        defaultCategoryShouldNotBeFound("modification_date.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoryShouldBeFound(String filter) throws Exception {
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].parentCategoryCode").value(hasItem(DEFAULT_PARENT_CATEGORY_CODE.intValue())))
            .andExpect(jsonPath("$.[*].parent_category_id").value(hasItem(DEFAULT_PARENT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].is_hidden").value(hasItem(DEFAULT_IS_HIDDEN.booleanValue())))
            .andExpect(jsonPath("$.[*].creation_date").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modification_date").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));

        // Check, that the count call also returns 1
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoryShouldNotBeFound(String filter) throws Exception {
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findById(category.getId()).get();
        // Disconnect from session so that the updates on updatedCategory are not directly saved in db
        em.detach(updatedCategory);
        updatedCategory
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .parentCategoryCode(UPDATED_PARENT_CATEGORY_CODE)
            .parent_category_id(UPDATED_PARENT_CATEGORY_ID)
            .is_hidden(UPDATED_IS_HIDDEN)
            .creation_date(UPDATED_CREATION_DATE)
            .modification_date(UPDATED_MODIFICATION_DATE);

        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategory))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategory.getParentCategoryId()).isEqualTo(UPDATED_PARENT_CATEGORY_CODE);
        assertThat(testCategory.getParent_category_id()).isEqualTo(UPDATED_PARENT_CATEGORY_ID);
        assertThat(testCategory.getIsHidden()).isEqualTo(UPDATED_IS_HIDDEN);
        assertThat(testCategory.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCategory.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, category.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(category))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(category))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoryWithPatch() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category using partial update
        Category partialUpdatedCategory = new Category();
        partialUpdatedCategory.setId(category.getId());

        partialUpdatedCategory
            .code(UPDATED_CODE)
            .parentCategoryCode(UPDATED_PARENT_CATEGORY_CODE)
            .parent_category_id(UPDATED_PARENT_CATEGORY_ID)
            .is_hidden(UPDATED_IS_HIDDEN);

        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategory))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategory.getParentCategoryId()).isEqualTo(UPDATED_PARENT_CATEGORY_CODE);
        assertThat(testCategory.getParent_category_id()).isEqualTo(UPDATED_PARENT_CATEGORY_ID);
        assertThat(testCategory.getIsHidden()).isEqualTo(UPDATED_IS_HIDDEN);
        assertThat(testCategory.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCategory.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCategoryWithPatch() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category using partial update
        Category partialUpdatedCategory = new Category();
        partialUpdatedCategory.setId(category.getId());

        partialUpdatedCategory
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .parentCategoryCode(UPDATED_PARENT_CATEGORY_CODE)
            .parent_category_id(UPDATED_PARENT_CATEGORY_ID)
            .is_hidden(UPDATED_IS_HIDDEN)
            .creation_date(UPDATED_CREATION_DATE)
            .modification_date(UPDATED_MODIFICATION_DATE);

        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategory))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategory.getParentCategoryId()).isEqualTo(UPDATED_PARENT_CATEGORY_CODE);
        assertThat(testCategory.getParent_category_id()).isEqualTo(UPDATED_PARENT_CATEGORY_ID);
        assertThat(testCategory.getIsHidden()).isEqualTo(UPDATED_IS_HIDDEN);
        assertThat(testCategory.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCategory.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, category.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(category))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(category))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Delete the category
        restCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, category.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
