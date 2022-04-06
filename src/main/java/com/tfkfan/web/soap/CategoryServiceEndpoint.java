package com.tfkfan.web.soap;

import com.tfkfan.domain.Category;
import com.tfkfan.service.CategoryService;
import com.tfkfan.web.soap.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import com.tfkfan.webservices.categoryservice.*;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryServiceEndpoint implements CategoryServicePorts {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryServiceEndpoint(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest parameters) {

        Category category = categoryService.save(parameters);

        ObjectFactory objectFactory = new ObjectFactory();
        CreateCategoryResponse response = objectFactory.createCreateCategoryResponse();
        response.setCategory(categoryMapper.toDto(category));

        return response;
    }

}
