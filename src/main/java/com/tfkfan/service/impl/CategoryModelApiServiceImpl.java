package com.tfkfan.service.impl;

import com.tfkfan.domain.Category;
import com.tfkfan.domain.CategoryProductModel;
import com.tfkfan.domain.ProductModel;
import com.tfkfan.domain.pk.CategoryProductModelPK;
import com.tfkfan.exception.EntityAlreadyExistsException;
import com.tfkfan.exception.EntityNotFoundException;
import com.tfkfan.repository.CategoryProductModelRepository;
import com.tfkfan.service.CategoryApiService;
import com.tfkfan.service.CategoryModelApiService;
import com.tfkfan.service.ProductModelApiService;
import com.tfkfan.webservices.types.LinkCategoryToModelRequest;
import com.tfkfan.webservices.types.UnlinkCategoryToModelRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryModelApiServiceImpl implements CategoryModelApiService {
    private final Logger log = LoggerFactory.getLogger(CategoryModelApiServiceImpl.class);

    private final CategoryApiService categoryApiService;
    private final ProductModelApiService productModelApiService;
    private final CategoryProductModelRepository repository;

    public CategoryModelApiServiceImpl(
        CategoryApiService categoryApiService, ProductModelApiService productModelApiService, CategoryProductModelRepository repository) {
        this.categoryApiService = categoryApiService;
        this.productModelApiService = productModelApiService;
        this.repository = repository;
    }


    @Override
    public CategoryProductModel linkCategoryToModel(LinkCategoryToModelRequest request) {
        Category category = categoryApiService.findOneByCode(request.getCategoryCode())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Категория с кодом %s не найдена", request.getCategoryCode()), Category.ENTITY_NAME));
        ProductModel model = productModelApiService.findOneByCode(request.getModelCode())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Модель с кодом %s не найдена", request.getModelCode()), ProductModel.ENTITY_NAME));

        CategoryProductModel catmodel = new CategoryProductModel(category, model);
        repository.findById(catmodel.getId()).ifPresent(categoryProductModel -> {
            throw new EntityAlreadyExistsException(String.format("Связь категории %s с моделью %s уже существует",
                request.getCategoryCode(), request.getModelCode()), CategoryProductModel.ENTITY_NAME);
        });

        return repository.save(catmodel);
    }

    @Override
    public CategoryProductModel unlinkCategoryToModel(UnlinkCategoryToModelRequest request) {
        Category category = categoryApiService.findOneByCode(request.getCategoryCode())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Категория с кодом %s не найдена", request.getCategoryCode()), Category.ENTITY_NAME));
        ProductModel model = productModelApiService.findOneByCode(request.getModelCode())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Модель с кодом %s не найдена", request.getModelCode()), ProductModel.ENTITY_NAME));

        CategoryProductModelPK PK = new CategoryProductModelPK(category.getId(), model.getId());
        Optional<CategoryProductModel> entity = repository.findById(PK);

        return entity.map(e -> {
            repository.delete(e);
            return e;
        }).orElseThrow(() -> new EntityNotFoundException(String.format("Связь категории %s с моделью %s не найдена",
            request.getCategoryCode(), request.getModelCode()), ProductModel.ENTITY_NAME));
    }
}
