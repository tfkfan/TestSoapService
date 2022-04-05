package com.tfkfan.exception;

import com.tfkfan.webservices.categoryservice.BaseFault;

/**
 * @author Baltser Artem tfkfan
 */
public class EntityAlreadyExists extends EntityTypeDefinedException{
    static final ExceptionDictionary dict = ExceptionDictionary.ENTITY_ALREADY_EXISTS_EXCEPTION;

    public EntityAlreadyExists(String entityType) {
        super(dict, entityType);
    }

    public EntityAlreadyExists(String message, String entityType) {
        super(message, dict.getCode(), entityType);
    }

    public EntityAlreadyExists(String message, Throwable cause, String entityType) {
        super(message, cause, dict.getCode(), entityType);
    }

    public EntityAlreadyExists(Throwable cause, String entityType) {
        super(cause, dict.getCode(), entityType);
    }

    public EntityAlreadyExists(String message, Throwable cause,
                               boolean enableSuppression, boolean writableStackTrace,
                               String entityType) {
        super(message, cause, enableSuppression, writableStackTrace, dict.getCode(), entityType);
    }

    @Override
    public BaseFault convert() {
        com.tfkfan.webservices.categoryservice.EntityAlreadyExistsException fault = new com.tfkfan.webservices.categoryservice.EntityAlreadyExistsException();
        fault.setEntityType(getEntityType());
        fault.setMessage(getMessage());
        return fault;
    }
}
