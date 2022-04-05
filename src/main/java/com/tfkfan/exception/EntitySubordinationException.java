package com.tfkfan.exception;

import com.tfkfan.webservices.categoryservice.BaseFault;
import com.tfkfan.webservices.categoryservice.Fault;

/**
 * @author Baltser Artem tfkfan
 */
public class EntitySubordinationException extends EntityTypeDefinedException {
    static final ExceptionDictionary dict = ExceptionDictionary.ENTITY_SUBORDINATION_EXCEPTION;

    public EntitySubordinationException(String entityType) {
        super(dict, entityType);
    }

    public EntitySubordinationException(String message, String entityType) {
        super(message, dict.getCode(), entityType);
    }

    public EntitySubordinationException(String message, Throwable cause, String entityType) {
        super(message, cause, dict.getCode(), entityType);
    }

    public EntitySubordinationException(Throwable cause, String entityType) {
        super(cause, dict.getCode(), entityType);
    }

    public EntitySubordinationException(String message, Throwable cause,
                                   boolean enableSuppression, boolean writableStackTrace,
                                   String entityType) {
        super(message, cause, enableSuppression, writableStackTrace, dict.getCode(), entityType);
    }
    @Override
    public BaseFault convert() {
        com.tfkfan.webservices.categoryservice.EntitySubordinationException fault = new com.tfkfan.webservices.categoryservice.EntitySubordinationException();
        fault.setEntityType(getEntityType());
        fault.setMessage(getMessage());
        return fault;
    }
}
