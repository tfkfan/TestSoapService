package com.tfkfan.web.soap;

import com.tfkfan.service.CategoryApiService;
import com.tfkfan.webservices.types.*;
import org.springframework.stereotype.Service;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryServiceEndpoint implements CategoryServicePorts {
    private final CategoryApiService apiService;

    public CategoryServiceEndpoint(CategoryApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) throws Fault {
        return apiService.save(createCategoryRequest);
    }

    @Override
    public UpdateCategoryResponse updateCategory(UpdateCategoryRequest updateCategoryRequest) throws Fault {
        return apiService.update(updateCategoryRequest);
    }

    @Override
    public FindCategoriesResponse findCategories(FindCategoriesRequest findCategoriesRequest) throws Fault {
        return apiService.findAll(findCategoriesRequest);
    }
}
