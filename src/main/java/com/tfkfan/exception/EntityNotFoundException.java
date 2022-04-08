package com.tfkfan.exception;

import com.tfkfan.domain.Category;
import com.tfkfan.domain.CategoryProductModel;
import com.tfkfan.domain.ProductModel;
import com.tfkfan.webservices.types.BaseFault;
import com.tfkfan.webservices.types.Fault;
import org.apache.cxf.Bus;

/**
 * @author Baltser Artem tfkfan
 */
public class EntityNotFoundException extends EntityTypeDefinedException {
    static final ExceptionDictionary dict = ExceptionDictionary.ENTITY_NOT_FOUND_EXCEPTION;

    public EntityNotFoundException(String entityType) {
        super(dict, entityType);
    }

    public EntityNotFoundException(String message, String entityType) {
        super(message, dict.getCode(), entityType);
    }

    public EntityNotFoundException(String message, Throwable cause, String entityType) {
        super(message, cause, dict.getCode(), entityType);
    }

    public EntityNotFoundException(Throwable cause, String entityType) {
        super(cause, dict.getCode(), entityType);
    }

    public EntityNotFoundException(String message, Throwable cause,
                                   boolean enableSuppression, boolean writableStackTrace,
                                   String entityType) {
        super(message, cause, enableSuppression, writableStackTrace, dict.getCode(), entityType);
    }

    public static EntityNotFoundException buildForParentCategory(String parentCategoryCode) {
        return new EntityNotFoundException(String.format("Родительская категория с кодом %s не найдена", parentCategoryCode)
            , Category.ENTITY_NAME);
    }

    public static EntityNotFoundException buildForCategory(String code) {
        return new EntityNotFoundException(String.format("Категория с кодом %s не найдена", code), Category.ENTITY_NAME);
    }

    public static EntityNotFoundException buildForModel(String code) {
        return new EntityNotFoundException(String.format("Модель с кодом %s не найдена", code), ProductModel.ENTITY_NAME);
    }

    public static EntityNotFoundException buildForCategoryModel(String categoryCode, String modelCode) {
        return new EntityNotFoundException(String.format("Связь категории %s с моделью %s не найдена",
            categoryCode, modelCode), CategoryProductModel.ENTITY_NAME);
    }

    @Override
    public BaseFault convert() {
        com.tfkfan.webservices.types.EntityNotFoundException fault = new com.tfkfan.webservices.types.EntityNotFoundException();
        fault.setEntityType(getEntityType());
        fault.setMessage(getMessage());
        return fault;
    }
}
