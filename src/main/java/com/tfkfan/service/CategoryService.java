package com.tfkfan.service;

import com.tfkfan.domain.Category;
import java.util.Optional;

import com.tfkfan.webservices.categoryservice.CreateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Category}.
 */
public interface CategoryService {
    /**
     * Save a request.
     *
     * @param request the entity to save.
     * @return the persisted entity.
     */
    Category save(CreateCategoryRequest request);

    /**
     * Partially updates a category.
     *
     * @param category the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Category> partialUpdate(Category category);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Category> findAll(Pageable pageable);

    /**
     * Get the "id" category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Category> findOne(Long id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
