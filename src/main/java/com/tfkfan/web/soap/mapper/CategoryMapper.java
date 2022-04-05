package com.tfkfan.web.soap.mapper;

import com.tfkfan.domain.Category;
import com.tfkfan.webservices.categoryservice.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Baltser Artem tfkfan
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CreateCategoryRequest request);
    com.tfkfan.webservices.categoryservice.Category toDto(Category category);
}
