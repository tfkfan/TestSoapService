package com.tfkfan.service.impl;

import com.tfkfan.domain.Category;
import com.tfkfan.exception.EntityAlreadyExistsException;
import com.tfkfan.exception.EntityNotFoundException;
import com.tfkfan.exception.EntitySubordinationException;
import com.tfkfan.repository.CategoryRepository;
import com.tfkfan.service.criteria.CategoryQueryService;
import com.tfkfan.service.CategoryService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tfkfan.service.criteria.model.CategoryCriteria;
import com.tfkfan.web.soap.mapper.CategoryMapper;
import com.tfkfan.webservices.types.CreateCategoryRequest;
import com.tfkfan.webservices.types.FindCategoriesRequest;
import com.tfkfan.webservices.types.UpdateCategoryRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl extends BasePageableServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryQueryService categoryQueryService;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository, CategoryQueryService categoryQueryService) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
        this.categoryQueryService = categoryQueryService;
    }

    @Override
    public Category save(CreateCategoryRequest request) {
        log.debug("Request to save Category : {}", request);
        if (!StringUtils.isEmpty(request.getParentCategoryCode()) && request.getCode().equals(request.getParentCategoryCode()))
            throw new EntitySubordinationException("Категория не может являться родительской по отношению к самой себе",
                Category.ENTITY_NAME);

        Category parent = null;

        if (!StringUtils.isEmpty(request.getParentCategoryCode())) {
            CategoryCriteria criteria = new CategoryCriteria();
            criteria.setCode((StringFilter) new StringFilter().setEquals(request.getParentCategoryCode()));
            criteria.setParentCategoryId((LongFilter) new LongFilter().setEquals(null));

            parent = categoryQueryService
                .findOneByCriteria(criteria)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Родительская категория с кодом %s не найдена", request.getParentCategoryCode())
                    , Category.ENTITY_NAME));
        }

        CategoryCriteria criteria = new CategoryCriteria();
        criteria.setCode((StringFilter) new StringFilter().setEquals(request.getCode()));

        categoryQueryService.findOneByCriteria(criteria).ifPresent((e) -> {
            throw new EntityAlreadyExistsException(String.format("Категория с кодом %s уже существует", request.getCode()), Category.ENTITY_NAME);
        });

        Category category = categoryMapper.toEntity(request);

        if (Objects.nonNull(parent))
            category.setParentCategoryId(parent.getId());

        return categoryRepository.save(category);
    }

    @Override
    public Category update(UpdateCategoryRequest request) {
        log.debug("Request to partially update Category : {}", request);

        CategoryCriteria criteria = new CategoryCriteria();
        criteria.setCode((StringFilter) new StringFilter().setEquals(request.getCode()));

        Category entity = categoryQueryService
            .findOneByCriteria(criteria)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Категория с кодом %s не найдена", request.getCode())));

        if (!StringUtils.isEmpty(request.getParentCategoryCode()) && entity.getCode().equals(request.getParentCategoryCode()))
            throw new EntitySubordinationException("Категория не может являться родительской по отношению к самой себе",
                Category.ENTITY_NAME);

        Category parent = null;
        if (!StringUtils.isEmpty(request.getParentCategoryCode())) {
            criteria = new CategoryCriteria();
            criteria.setCode((StringFilter) new StringFilter().setEquals(request.getParentCategoryCode()));
            criteria.setParentCategoryIdNull(true);

            parent = categoryQueryService
                .findOneByCriteria(criteria)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Родительская категория с кодом %s не найдена", request.getParentCategoryCode())
                    , Category.ENTITY_NAME));
        }

        if (Objects.nonNull(parent))
            entity.setParentCategoryId(parent.getId());
        if (Objects.nonNull(request.getName()))
            entity.setName(request.getName());
        if (Objects.nonNull(request.isIsHidden()))
            entity.setIsHidden(request.isIsHidden());

        entity.setDescription(request.getDescription());

        return categoryRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findAll(FindCategoriesRequest request) {
        log.debug("Request to get all Categories");

        Pageable pageable = pageable(request.getPageSettings());

        if (!request.getParentCategoryCodes().isEmpty()) {
            CategoryCriteria criteria = new CategoryCriteria();
            criteria.code((StringFilter) new StringFilter().setIn(request.getParentCategoryCodes()));
            criteria.setParentCategoryIdNull(true);
            List<Category> parentCategories = categoryQueryService.findByCriteria(criteria);

            criteria = new CategoryCriteria();
            criteria.setParentCategoryId((LongFilter) new LongFilter().setIn(parentCategories.stream().map(Category::getId).collect(Collectors.toList())));
            request.getCodes().addAll(categoryQueryService
                .findByCriteria(criteria)
                .stream()
                .map(Category::getCode)
                .collect(Collectors.toList()));
        }

        CategoryCriteria criteria = new CategoryCriteria();
        if (!request.getCodes().isEmpty())
            criteria.setCode((StringFilter) new StringFilter().setIn(request.getCodes()));
        if(!StringUtils.isEmpty(request.getName()))
            criteria.setName(new StringFilter().setContains(request.getName()));
        if(!StringUtils.isEmpty(request.getDescription()))
            criteria.setDescription(new StringFilter().setContains(request.getDescription()));
        if(Objects.nonNull(request.isIsHidden()))
            criteria.setIsHidden((BooleanFilter) new BooleanFilter().setEquals(request.isIsHidden()));
        if(Objects.nonNull(request.isOnlyParent()))
            criteria.setParentCategoryIdNull(true);

        return categoryQueryService.findByCriteria(criteria, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
