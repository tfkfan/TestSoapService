package com.tfkfan.service;

import com.tfkfan.domain.CategoryProductModel;
import com.tfkfan.webservices.types.LinkCategoryToModelRequest;
import com.tfkfan.webservices.types.UnlinkCategoryToModelRequest;

/**
 * @author Baltser Artem tfkfan
 */
public interface CategoryModelApiService {
    CategoryProductModel linkCategoryToModel(LinkCategoryToModelRequest request);

    CategoryProductModel unlinkCategoryToModel(UnlinkCategoryToModelRequest request);
}
