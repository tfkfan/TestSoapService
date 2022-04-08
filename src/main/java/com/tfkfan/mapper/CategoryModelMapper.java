package com.tfkfan.mapper;

import com.tfkfan.domain.Category;
import com.tfkfan.domain.CategoryProductModel;
import com.tfkfan.domain.ProductModel;
import com.tfkfan.webservices.types.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

/**
 * @author Baltser Artem tfkfan
 */
@Mapper(componentModel = "spring")
public abstract class CategoryModelMapper implements BaseMapper{
    @Autowired CategoryMapper categoryMapper;
    @Autowired ProductModelMapper modelMapper;

    public LinkCategoryToModelResponse toLinkResponse(CategoryProductModel entity){
        LinkCategoryToModelResponse response =  new LinkCategoryToModelResponse();
        response.setCategory(categoryMapper.toDto(entity.getCategory()));
        response.setModel(modelMapper.toDto(entity.getModel()));

        return response;
    }

    public UnlinkCategoryToModelResponse toUnlinkResponse(CategoryProductModel entity){
        UnlinkCategoryToModelResponse response =  new UnlinkCategoryToModelResponse();
        response.setCategory(categoryMapper.toDto(entity.getCategory()));
        response.setModel(modelMapper.toDto(entity.getModel()));

        return response;
    }

    public FindModelsByCategoryCodeResponse toFindModelsByCategoryCodeResponse(Category category, Page<ProductModel> page){
        FindModelsByCategoryCodeResponse resp = new FindModelsByCategoryCodeResponse();
        resp.setCategory(categoryMapper.toDto(category));
        resp.setPageInfo(pageInfo(page));

        ProductModels models = new ProductModels();
        models.setModel(modelMapper.toDtos(page.getContent()));
        resp.setModels(models);

        return resp;
    }
}
