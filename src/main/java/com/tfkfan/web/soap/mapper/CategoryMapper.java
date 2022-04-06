package com.tfkfan.web.soap.mapper;

import com.tfkfan.domain.Category;
import com.tfkfan.webservices.types.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Baltser Artem tfkfan
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, com.tfkfan.webservices.types.Category> {
    @Mapping(target = "parentCategoryCode", ignore = true)
    Category toEntity(CreateCategoryRequest request);

    List<com.tfkfan.webservices.types.Category> toDtos(List<Category> entity);

    @Mapping(target = "category", source = "entity", qualifiedByName = DTO_MAPPING)
    CreateCategoryResponse toCreateResponse(Category entity);

    @Mapping(target = "category", source = "entity", qualifiedByName = DTO_MAPPING)
    UpdateCategoryResponse toUpdateResponse(Category entity);

    default FindCategoriesResponse toFindResponse(Page<Category> page) {
        FindCategoriesResponse resp = new FindCategoriesResponse();
        Categories categories = new Categories();
        categories.setCategory(toDtos(page.getContent()));
        resp.setPageInfo(pageInfo(page));

        return resp;
    }
}
