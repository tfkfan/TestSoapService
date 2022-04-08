package com.tfkfan.service;

import com.tfkfan.domain.Category;
import java.util.Optional;

import com.tfkfan.webservices.types.CreateCategoryRequest;
import com.tfkfan.webservices.types.FindCategoriesRequest;
import com.tfkfan.webservices.types.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Category}.
 */
public interface CategoryApiService extends PageableService{
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
     * @param request the entity to update partially.
     * @return the persisted entity.
     */
    Category update(UpdateCategoryRequest request);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Category> findAll(Pageable pageable);

    /**
     * Get all the categories.
     *
     * @param request the request information.
     * @return the list of entities.
     */
    Page<Category> findAll(FindCategoriesRequest request);

    /**
     * Get the "id" category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Category> findOne(Long id);

    /**
     * Get the "code" category.
     *
     * @param code the code of the entity.
     * @return the entity.
     */
    Optional<Category> findOneByCode(String code);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
