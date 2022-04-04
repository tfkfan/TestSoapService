package com.tfkfan.web.soap;

import org.springframework.stereotype.Service;
import com.tfkfan.webservices.categoryservice.*;

/**
 * @author Baltser Artem tfkfan
 */
@Service
public class CategoryServiceEndpoint implements CategoryService {

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest parameters) {
        ObjectFactory objectFactory = new ObjectFactory();
        CreateCategoryResponse response = objectFactory.createCreateCategoryResponse();
        Category category = objectFactory.createCategory();
        category.setCode("CODE123");
        category.setParentCategoryCode(53434L);
        category.setName("NAME123");
        category.setDescription("DESC1234");
        response.setCategory(category);
        return response;
      /*  ObjectFactory factory = new ObjectFactory();
        AccountDetailsResponse response = factory.createAccountDetailsResponse();

        Account account = factory.createAccount();
        account.setAccountNumber("12345");
        account.setAccountStatus(EnumAccountStatus.ACTIVE);
        account.setAccountName("Joe Bloggs");
        account.setAccountBalance(3400);

        response.setAccountDetails(account);
        return response;*/
    }

}
