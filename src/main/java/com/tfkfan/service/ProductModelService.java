package com.tfkfan.service;

import com.tfkfan.domain.Category;
import com.tfkfan.domain.ProductModel;
import com.tfkfan.webservices.types.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ProductModel}.
 */
public interface ProductModelService {
    /**
     * Save a request.
     *
     * @param request the entity to save.
     * @return the persisted entity.
     */
    ProductModel save(CreateModelRequest request);

    /**
     * Partially updates a category.
     *
     * @param request the entity to update partially.
     * @return the persisted entity.
     */
    ProductModel update(UpdateModelRequest request);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductModel> findAll(Pageable pageable);


    Page<ProductModel> findAll(FindModelsRequest request);

    /**
     * Get the "id" category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductModel> findOne(Long id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
