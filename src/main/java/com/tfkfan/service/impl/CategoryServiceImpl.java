package com.tfkfan.service.impl;

import com.tfkfan.domain.Category;
import com.tfkfan.exception.EntityAlreadyExists;
import com.tfkfan.exception.EntityNotFoundException;
import com.tfkfan.exception.EntitySubordinationException;
import com.tfkfan.repository.CategoryRepository;
import com.tfkfan.service.CategoryQueryService;
import com.tfkfan.service.CategoryService;

import java.util.Optional;

import com.tfkfan.service.criteria.CategoryCriteria;
import com.tfkfan.web.soap.mapper.CategoryMapper;
import com.tfkfan.webservices.categoryservice.CreateCategoryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

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
        if (request.getCode().equals(request.getParentCategoryCode()))
            throw new EntitySubordinationException(Category.ENTITY_NAME);

        CategoryCriteria criteria = new CategoryCriteria();
        criteria.setCode((StringFilter) new StringFilter().setEquals(request.getParentCategoryCode()));
        criteria.setParentCategoryId((LongFilter) new LongFilter().setEquals(null));

        Category parent = categoryQueryService
            .findOneByCriteria(criteria)
            .orElseThrow(() -> new EntityNotFoundException(Category.ENTITY_NAME));

        criteria = new CategoryCriteria();
        criteria.setCode((StringFilter) new StringFilter().setEquals(request.getCode()));

        categoryQueryService.findOneByCriteria(criteria).ifPresent((e) -> {
            throw new EntityAlreadyExists(Category.ENTITY_NAME);
        });

        Category category = categoryMapper.toEntity(request);
        category.setParentCategoryId(parent.getId());

        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> partialUpdate(Category category) {
        log.debug("Request to partially update Category : {}", category);

        return categoryRepository
            .findById(category.getId())
            .map(existingCategory -> {
                if (category.getCode() != null) {
                    existingCategory.setCode(category.getCode());
                }
                if (category.getName() != null) {
                    existingCategory.setName(category.getName());
                }
                if (category.getDescription() != null) {
                    existingCategory.setDescription(category.getDescription());
                }
                if (category.getParentCategoryId() != null) {
                    existingCategory.setParentCategoryId(category.getParentCategoryId());
                }
                if (category.getIsHidden() != null) {
                    existingCategory.setIsHidden(category.getIsHidden());
                }
                if (category.getCreationDate() != null) {
                    existingCategory.setCreationDate(category.getCreationDate());
                }
                if (category.getModificationDate() != null) {
                    existingCategory.setModificationDate(category.getModificationDate());
                }

                return existingCategory;
            })
            .map(categoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable);
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
