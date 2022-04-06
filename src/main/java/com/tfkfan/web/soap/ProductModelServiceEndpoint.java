package com.tfkfan.web.soap;

import com.tfkfan.domain.ProductModel;
import com.tfkfan.service.ProductModelService;
import com.tfkfan.web.soap.mapper.ProductModelMapper;
import com.tfkfan.webservices.types.*;
import org.springframework.stereotype.Service;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class ProductModelServiceEndpoint implements ProductModelServicePorts {
    private final ProductModelService entityService;
    private final ProductModelMapper mapper;

    public ProductModelServiceEndpoint(ProductModelService entityService, ProductModelMapper mapper) {
        this.entityService = entityService;
        this.mapper = mapper;
    }

    @Override
    public CreateModelResponse createModel(CreateModelRequest createModelRequest) throws Fault {
        return mapper.toCreateResponse(entityService.save(createModelRequest));
    }

    @Override
    public UpdateModelResponse updateModel(UpdateModelRequest updateModelRequest) throws Fault {
        return mapper.toUpdateResponse(entityService.update(updateModelRequest));
    }

    @Override
    public FindModelsResponse findModels(FindModelsRequest findModelsRequest) throws Fault {
        return mapper.toFindResponse(entityService.findAll(findModelsRequest));
    }
}
