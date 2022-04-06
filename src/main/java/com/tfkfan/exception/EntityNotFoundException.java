package com.tfkfan.exception;

import com.tfkfan.webservices.types.BaseFault;
import com.tfkfan.webservices.types.Fault;
import org.apache.cxf.Bus;

/**
 * @author Baltser Artem tfkfan
 */
public class EntityNotFoundException extends EntityTypeDefinedException{
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

    @Override
    public BaseFault convert() {
        com.tfkfan.webservices.types.EntityNotFoundException fault = new com.tfkfan.webservices.types.EntityNotFoundException();
        fault.setEntityType(getEntityType());
        fault.setMessage(getMessage());
        return fault;
    }
}
