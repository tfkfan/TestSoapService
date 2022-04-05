package com.tfkfan.web.soap;

import com.tfkfan.domain.Category;
import com.tfkfan.exception.EntityNotFoundException;
import com.tfkfan.service.CategoryService;
import com.tfkfan.web.soap.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import com.tfkfan.webservices.categoryservice.*;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryServiceEndpoint implements CategoryServicePorts {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public CategoryServiceEndpoint(CategoryMapper categoryMapper, CategoryService categoryService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest parameters) {
        Category category = categoryMapper.toEntity(parameters);
        category = categoryService.save(category);

        ObjectFactory objectFactory = new ObjectFactory();
        CreateCategoryResponse response = objectFactory.createCreateCategoryResponse();
        response.setCategory(categoryMapper.toDto(category));

        return response;
    }

}
