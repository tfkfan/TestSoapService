package com.tfkfan.service.impl;

import com.tfkfan.domain.Category;
import com.tfkfan.repository.CategoryRepository;
import com.tfkfan.service.CategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        log.debug("Request to save Category : {}", category);
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
                if (category.getParentCategoryCode() != null) {
                    existingCategory.setParentCategoryCode(category.getParentCategoryCode());
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
