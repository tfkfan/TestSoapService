package com.tfkfan.exception;

import lombok.Getter;

/**
 * @author Baltser Artem tfkfan
 */
public abstract class EntityTypeDefinedException extends BusinessException{
    private final String entityType;

    public EntityTypeDefinedException(String code, String entityType) {
        super(code);
        this.entityType = entityType;
    }

    public EntityTypeDefinedException(String message, String code, String entityType) {
        super(message, code);
        this.entityType = entityType;
    }

    public EntityTypeDefinedException(ExceptionDictionary dictionary, String entityType) {
        super(dictionary);
        this.entityType = entityType;
    }

    public EntityTypeDefinedException(ExceptionDictionary dictionary, String message, String entityType) {
        super(dictionary, message);
        this.entityType = entityType;
    }

    public EntityTypeDefinedException(String message, Throwable cause, String code, String entityType) {
        super(message, cause, code);
        this.entityType = entityType;
    }

    public EntityTypeDefinedException(Throwable cause, String code, String entityType) {
        super(cause, code);
        this.entityType = entityType;
    }

    public EntityTypeDefinedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String entityType) {
        super(message, cause, enableSuppression, writableStackTrace, code);
        this.entityType = entityType;
    }

    public String getEntityType() {
        return entityType;
    }
}
