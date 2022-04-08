package com.tfkfan.service;

import com.tfkfan.webservices.types.*;

/**
 * @author Baltser Artem tfkfan
 */
public interface CategoryModelApiService {
    LinkCategoryToModelResponse linkCategoryToModel(LinkCategoryToModelRequest request);

    UnlinkCategoryToModelResponse unlinkCategoryToModel(UnlinkCategoryToModelRequest request);

    FindModelsByCategoryCodeResponse findModelsByCategoryCode(FindModelsByCategoryCodeRequest request);
}
