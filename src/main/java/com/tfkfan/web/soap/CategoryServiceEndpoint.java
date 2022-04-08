package com.tfkfan.web.soap;

import com.tfkfan.service.CategoryApiService;
import com.tfkfan.mapper.CategoryMapper;
import com.tfkfan.webservices.types.*;
import org.springframework.stereotype.Service;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryServiceEndpoint implements CategoryServicePorts {
    private final CategoryApiService apiService;
    private final CategoryMapper mapper;

    public CategoryServiceEndpoint(CategoryApiService apiService, CategoryMapper mapper) {
        this.apiService = apiService;
        this.mapper = mapper;
    }

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) throws Fault {
        return mapper.toCreateResponse(apiService.save(createCategoryRequest));
    }

    @Override
    public UpdateCategoryResponse updateCategory(UpdateCategoryRequest updateCategoryRequest) throws Fault {
        return mapper.toUpdateResponse(apiService.update(updateCategoryRequest));
    }

    @Override
    public FindCategoriesResponse findCategories(FindCategoriesRequest findCategoriesRequest) throws Fault {
        return mapper.toFindResponse(apiService.findAll(findCategoriesRequest));
    }

}
