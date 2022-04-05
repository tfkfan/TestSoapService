package com.tfkfan.exception;

import lombok.Getter;

/**
 * @author Baltser Artem tfkfan
 */
public enum ExceptionDictionary {
    ENTITY_SUBORDINATION_EXCEPTION("CODE_1","DEFAULT MSG 1"),
    ENTITY_NOT_FOUND_EXCEPTION("CODE_2", "DEFAULT MSG 2"),
    DATABASE_EXCEPTION("CODE_3","DEFAULT MSG 3"),
    ENTITY_ALREADY_EXISTS_EXCEPTION("CODE_4","DEFAULT_MSG 4");

    private String code;
    private String message;

    ExceptionDictionary(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
