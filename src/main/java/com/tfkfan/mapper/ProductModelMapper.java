package com.tfkfan.mapper;

import com.tfkfan.domain.ProductModel;
import com.tfkfan.webservices.types.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Baltser Artem tfkfan
 */
@Mapper(componentModel = "spring")
public interface ProductModelMapper extends DtoMapper<ProductModel, com.tfkfan.webservices.types.ProductModel>{
    com.tfkfan.domain.ProductModel toEntity(CreateModelRequest request);

    List<com.tfkfan.webservices.types.ProductModel> toDtos(List<ProductModel> entities);

    @Mapping(target = "model", source = "entity", qualifiedByName = DTO_MAPPING)
    CreateModelResponse toCreateResponse(ProductModel entity);

    @Mapping(target = "model", source = "entity", qualifiedByName = DTO_MAPPING)
    UpdateModelResponse toUpdateResponse(ProductModel entity);

    default FindModelsResponse toFindResponse(Page<ProductModel> page) {
        FindModelsResponse resp = new FindModelsResponse();
        ProductModels models = new ProductModels();
        models.setModel(toDtos(page.getContent()));
        resp.setPageInfo(pageInfo(page));
        resp.setModels(models);
        return resp;
    }
}
