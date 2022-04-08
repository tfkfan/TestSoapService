package com.tfkfan.web.soap;

import com.tfkfan.mapper.CategoryModelMapper;
import com.tfkfan.service.CategoryModelApiService;
import com.tfkfan.service.ProductModelApiService;
import com.tfkfan.mapper.ProductModelMapper;
import com.tfkfan.webservices.types.*;
import org.springframework.stereotype.Service;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class ProductModelServiceEndpoint implements ProductModelServicePorts {
    private final ProductModelApiService apiService;
    private final CategoryModelApiService categoryModelApiService;
    private final ProductModelMapper mapper;
    private final CategoryModelMapper categoryModelMapper;

    public ProductModelServiceEndpoint(ProductModelApiService apiService, CategoryModelApiService categoryModelApiService, ProductModelMapper mapper, CategoryModelMapper categoryModelMapper) {
        this.apiService = apiService;
        this.categoryModelApiService = categoryModelApiService;
        this.mapper = mapper;
        this.categoryModelMapper = categoryModelMapper;
    }

    @Override
    public CreateModelResponse createModel(CreateModelRequest createModelRequest) throws Fault {
        return mapper.toCreateResponse(apiService.save(createModelRequest));
    }

    @Override
    public UpdateModelResponse updateModel(UpdateModelRequest updateModelRequest) throws Fault {
        return mapper.toUpdateResponse(apiService.update(updateModelRequest));
    }

    @Override
    public LinkCategoryToModelResponse linkCategoryToModel(LinkCategoryToModelRequest linkCategoryToModelRequest) throws Fault {
        return categoryModelMapper.toLinkResponse(categoryModelApiService.linkCategoryToModel(linkCategoryToModelRequest));
    }

    @Override
    public UnlinkCategoryToModelResponse unlinkCategoryToModel(UnlinkCategoryToModelRequest unlinkCategoryToModelRequest) throws Fault {
        return categoryModelMapper.toUnlinkResponse(categoryModelApiService.unlinkCategoryToModel(unlinkCategoryToModelRequest));
    }

    @Override
    public FindModelsResponse findModels(FindModelsRequest findModelsRequest) throws Fault {
        return mapper.toFindResponse(apiService.findAll(findModelsRequest));
    }
}
