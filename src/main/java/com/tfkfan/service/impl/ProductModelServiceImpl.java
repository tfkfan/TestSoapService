package com.tfkfan.service.impl;

import com.tfkfan.domain.ProductModel;
import com.tfkfan.repository.ProductModelRepository;
import com.tfkfan.service.criteria.ProductModelQueryService;
import com.tfkfan.service.ProductModelService;
import com.tfkfan.webservices.types.CreateModelRequest;
import com.tfkfan.webservices.types.FindModelsRequest;
import com.tfkfan.webservices.types.UpdateModelRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class ProductModelServiceImpl  extends BasePageableServiceImpl  implements ProductModelService {
    private final Logger log = LoggerFactory.getLogger(ProductModelServiceImpl.class);

    private final ProductModelRepository repository;
    private final ProductModelQueryService queryService;
    public ProductModelServiceImpl(ProductModelRepository repository, ProductModelQueryService queryService) {
        this.repository = repository;
        this.queryService = queryService;
    }

    @Override
    public ProductModel save(CreateModelRequest request) {
        return null;
    }

    @Override
    public ProductModel update(UpdateModelRequest request) {
        return null;
    }

    @Override
    public Page<ProductModel> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProductModel> findAll(FindModelsRequest request) {
        return null;
    }

    @Override
    public Optional<ProductModel> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
