package com.tfkfan.service.impl;

import com.tfkfan.domain.ProductModel;
import com.tfkfan.exception.EntityAlreadyExistsException;
import com.tfkfan.exception.EntityNotFoundException;
import com.tfkfan.mapper.ProductModelMapper;
import com.tfkfan.repository.ProductModelRepository;
import com.tfkfan.service.ProductModelApiService;
import com.tfkfan.service.criteria.ProductModelQueryService;
import com.tfkfan.service.criteria.model.ProductModelCriteria;
import com.tfkfan.webservices.types.CreateModelRequest;
import com.tfkfan.webservices.types.FindModelsRequest;
import com.tfkfan.webservices.types.UpdateModelRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.StringFilter;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class ProductModelApiServiceImpl extends BasePageableServiceImpl  implements ProductModelApiService {
    private final Logger log = LoggerFactory.getLogger(ProductModelApiServiceImpl.class);

    private final ProductModelMapper mapper;
    private final ProductModelRepository repository;
    private final ProductModelQueryService queryService;

    public ProductModelApiServiceImpl(ProductModelMapper mapper, ProductModelRepository repository, ProductModelQueryService queryService) {
        this.mapper = mapper;
        this.repository = repository;
        this.queryService = queryService;
    }

    @Override
    public ProductModel save(CreateModelRequest request) {
        log.debug("Request to save Model : {}", request);

        ProductModelCriteria criteria = new ProductModelCriteria();
        criteria.setCode((StringFilter) new StringFilter().setEquals(request.getCode()));

        queryService.findOneByCriteria(criteria).ifPresent((e) -> {
            throw new EntityAlreadyExistsException(String.format("Модель с кодом %s уже существует", request.getCode()), ProductModel.ENTITY_NAME);
        });

        ProductModel entity = mapper.toEntity(request);

        entity.setDescription(request.getDescription());
        entity.setName(request.getName());
        entity.setCode(request.getCode());

        return repository.save(entity);
    }

    @Override
    public ProductModel update(UpdateModelRequest request) {
        log.debug("Request to update Model : {}", request);

        ProductModelCriteria criteria = new ProductModelCriteria();
        criteria.setCode((StringFilter) new StringFilter().setEquals(request.getCode()));

        ProductModel entity = queryService
            .findOneByCriteria(criteria)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Модель с кодом %s не найдена", request.getCode()), ProductModel.ENTITY_NAME));

        if (Objects.nonNull(request.getName()))
            entity.setName(request.getName());

        entity.setDescription(request.getDescription());

        return repository.save(entity);
    }

    @Override
    public Page<ProductModel> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return repository.findAll(pageable);
    }

    @Override
    public Page<ProductModel> findAll(FindModelsRequest request) {
        log.debug("Request to get all Models");

        Pageable pageable = pageable(request.getPageSettings());

        ProductModelCriteria criteria = new ProductModelCriteria();
        if (!request.getCodes().isEmpty())
            criteria.setCode((StringFilter) new StringFilter().setIn(request.getCodes()));
        if(!StringUtils.isEmpty(request.getName()))
            criteria.setName(new StringFilter().setContains(request.getName()));
        if(!StringUtils.isEmpty(request.getDescription()))
            criteria.setDescription(new StringFilter().setContains(request.getDescription()));

        return queryService.findByCriteria(criteria, pageable);
    }

    @Override
    public Optional<ProductModel> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return repository.findById(id);
    }

    @Override
    public Optional<ProductModel> findOneByCode(String code) {
        log.debug("Request to get Model by code: {}", code);
        ProductModelCriteria criteria = new ProductModelCriteria();
        criteria.setCode((StringFilter) new StringFilter().setEquals(code));
        return queryService.findOneByCriteria(criteria);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        repository.deleteById(id);
    }
}
