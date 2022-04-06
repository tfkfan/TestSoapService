package com.tfkfan.web.soap;

import com.tfkfan.domain.Category;
import com.tfkfan.service.CategoryService;
import com.tfkfan.web.soap.mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.tfkfan.webservices.types.*;

import java.util.stream.Collectors;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryServiceEndpoint implements CategoryServicePorts {
    private final CategoryService entityService;
    private final CategoryMapper mapper;

    public CategoryServiceEndpoint(CategoryService entityService, CategoryMapper mapper) {
        this.entityService = entityService;
        this.mapper = mapper;
    }

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) throws Fault {
        return mapper.toCreateResponse(entityService.save(createCategoryRequest));
    }

    @Override
    public UpdateCategoryResponse updateCategory(UpdateCategoryRequest updateCategoryRequest) throws Fault {
        return mapper.toUpdateResponse(entityService.update(updateCategoryRequest));
    }

    @Override
    public FindCategoriesResponse findCategories(FindCategoriesRequest findCategoriesRequest) throws Fault {
        return mapper.toFindResponse(entityService.findAll(findCategoriesRequest));
    }

}
