package com.tfkfan.web.soap;

import com.tfkfan.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.tfkfan.webservices.categoryservice.*;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryServiceEndpoint implements CategoryServicePorts {

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest parameters) {

        throw new EntityNotFoundException("entityType1223523", "sdf");
      /*  ObjectFactory objectFactory = new ObjectFactory();
        CreateCategoryResponse response = objectFactory.createCreateCategoryResponse();
        Category category = objectFactory.createCategory();
        category.setCode("CODE123");
        category.setParentCategoryCode(53434L);
        category.setName("NAME123");
        category.setDescription("DESC1234");
        response.setCategory(category);
        return response;*/
    }

}
