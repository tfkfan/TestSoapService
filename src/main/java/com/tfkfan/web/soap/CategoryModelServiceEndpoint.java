package com.tfkfan.web.soap;

import com.tfkfan.service.CategoryModelApiService;
import com.tfkfan.webservices.types.*;
import org.springframework.stereotype.Service;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryModelServiceEndpoint implements CategoryModelServicePorts {
    private final CategoryModelApiService apiService;

    public CategoryModelServiceEndpoint(CategoryModelApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public LinkCategoryToModelResponse linkCategoryToModel(LinkCategoryToModelRequest linkCategoryToModelRequest) throws Fault {
        return apiService.linkCategoryToModel(linkCategoryToModelRequest);
    }
    @Override
    public UnlinkCategoryToModelResponse unlinkCategoryToModel(UnlinkCategoryToModelRequest unlinkCategoryToModelRequest) throws Fault {
        return apiService.unlinkCategoryToModel(unlinkCategoryToModelRequest);
    }
    @Override
    public FindModelsByCategoryCodeResponse findModelsByCategoryCode(FindModelsByCategoryCodeRequest findModelsByCategoryCodeRequest) throws Fault {
        return apiService.findModelsByCategoryCode(findModelsByCategoryCodeRequest);
    }
}
