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
public interface ProductModelApiService {
    /**
     * Save a request.
     *
     * @param request the entity to save.
     * @return the persisted entity.
     */
    ProductModel save(CreateModelRequest request);

    /**
     * updates a model.
     *
     * @param request the entity to update partially.
     * @return the persisted entity.
     */
    ProductModel update(UpdateModelRequest request);

    /**
     * Get all the models.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductModel> findAll(Pageable pageable);


    Page<ProductModel> findAll(FindModelsRequest request);

    /**
     * Get the "id" model.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductModel> findOne(Long id);

    /**
     * Get the "code" model.
     *
     * @param code the code of the entity.
     * @return the entity.
     */
    Optional<ProductModel> findOneByCode(String code);

    /**
     * Delete the "id" model.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
