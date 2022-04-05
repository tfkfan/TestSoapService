package com.tfkfan.exception;

import lombok.Getter;

/**
 * @author Baltser Artem tfkfan
 */
public abstract class BusinessException extends RuntimeException implements SoapFaultConvertable {
    private final String code;

    public BusinessException(String code) {
        this.code = code;
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BusinessException(ExceptionDictionary dictionary) {
        super(dictionary.getMessage());
        this.code = dictionary.getCode();
    }

    public BusinessException(ExceptionDictionary dictionary, String message) {
        super(message);
        this.code = dictionary.getCode();
    }

    public BusinessException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
