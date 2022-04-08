package com.tfkfan.service.impl;

import com.tfkfan.domain.ProductModel;
import com.tfkfan.exception.EntityAlreadyExistsException;
import com.tfkfan.exception.EntityNotFoundException;
import com.tfkfan.mapper.ProductModelMapper;
import com.tfkfan.repository.ProductModelRepository;
import com.tfkfan.service.ProductModelApiService;
import com.tfkfan.service.criteria.ProductModelQueryService;
import com.tfkfan.service.criteria.model.ProductModelCriteria;
import com.tfkfan.webservices.types.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.StringFilter;

import java.util.Objects;

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
    public CreateModelResponse save(CreateModelRequest request) {
        log.debug("Request to save Model : {}", request);

        queryService.findOneByCriteria(new ProductModelCriteria()
                .code((StringFilter) new StringFilter().setEquals(request.getCode())))
            .ifPresent((e) -> {
            throw EntityAlreadyExistsException.buildForModel(request.getCode());
        });

        ProductModel entity = mapper.toEntity(request);

        entity.setDescription(request.getDescription());
        entity.setName(request.getName());
        entity.setCode(request.getCode());

        return mapper.toCreateResponse(repository.save(entity));
    }

    @Override
    public UpdateModelResponse update(UpdateModelRequest request) {
        log.debug("Request to update Model : {}", request);

        final ProductModel entity = queryService
            .findOneByCriteria(new ProductModelCriteria()
                .code((StringFilter) new StringFilter().setEquals(request.getCode())))
            .orElseThrow(() -> EntityNotFoundException.buildForModel(request.getCode()));

        if (Objects.nonNull(request.getName()))
            entity.setName(request.getName());

        entity.setDescription(request.getDescription());

        return mapper.toUpdateResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public FindModelsResponse findAll(FindModelsRequest request) {
        log.debug("Request to get all Models");

        Pageable pageable = pageable(request.getPageSettings());

        ProductModelCriteria criteria = new ProductModelCriteria();
        if (!request.getCodes().isEmpty())
            criteria.setCode((StringFilter) new StringFilter().setIn(request.getCodes()));
        if(!StringUtils.isEmpty(request.getName()))
            criteria.setName(new StringFilter().setContains(request.getName()));
        if(!StringUtils.isEmpty(request.getDescription()))
            criteria.setDescription(new StringFilter().setContains(request.getDescription()));

        return mapper.toFindResponse(queryService.findByCriteria(criteria, pageable));
    }
}
