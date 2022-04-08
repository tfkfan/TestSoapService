package com.tfkfan.exception;

import com.tfkfan.domain.Category;
import com.tfkfan.domain.CategoryProductModel;
import com.tfkfan.domain.ProductModel;
import com.tfkfan.webservices.types.BaseFault;

/**
 * @author Baltser Artem tfkfan
 */
public class EntityAlreadyExistsException extends EntityTypeDefinedException{
    static final ExceptionDictionary dict = ExceptionDictionary.ENTITY_ALREADY_EXISTS_EXCEPTION;

    public EntityAlreadyExistsException(String entityType) {
        super(dict, entityType);
    }

    public EntityAlreadyExistsException(String message, String entityType) {
        super(message, dict.getCode(), entityType);
    }

    public EntityAlreadyExistsException(String message, Throwable cause, String entityType) {
        super(message, cause, dict.getCode(), entityType);
    }

    public EntityAlreadyExistsException(Throwable cause, String entityType) {
        super(cause, dict.getCode(), entityType);
    }

    public EntityAlreadyExistsException(String message, Throwable cause,
                                        boolean enableSuppression, boolean writableStackTrace,
                                        String entityType) {
        super(message, cause, enableSuppression, writableStackTrace, dict.getCode(), entityType);
    }

    public static EntityAlreadyExistsException buildForCategory(String code){
        return new EntityAlreadyExistsException(String.format("Категория с кодом %s уже существует",code), Category.ENTITY_NAME);
    }

    public static EntityAlreadyExistsException buildForModel(String code){
        return new EntityAlreadyExistsException(String.format("Модель с кодом %s уже существует", code), ProductModel.ENTITY_NAME);
    }

    public static EntityAlreadyExistsException buildForCategoryModel(String categoryCode, String modelCode){
        return new EntityAlreadyExistsException(String.format("Связь категории %s с моделью %s уже существует",
            categoryCode, modelCode), CategoryProductModel.ENTITY_NAME);
    }

    @Override
    public BaseFault convert() {
        com.tfkfan.webservices.types.EntityAlreadyExistsException fault = new com.tfkfan.webservices.types.EntityAlreadyExistsException();
        fault.setEntityType(getEntityType());
        fault.setMessage(getMessage());
        return fault;
    }
}
