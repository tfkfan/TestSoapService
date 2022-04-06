package com.tfkfan.exception;

import com.tfkfan.webservices.types.BaseFault;

/**
 * @author Baltser Artem tfkfan
 */
public class DatabaseException extends ScenarioException{
    static final ExceptionDictionary dict = ExceptionDictionary.DATABASE_EXCEPTION;

    public DatabaseException(String scenario) {
        super(dict, scenario);
    }

    public DatabaseException(String message, String scenario) {
        super(message, dict.getCode(), scenario);
    }

    public DatabaseException(String message, Throwable cause, String scenario) {
        super(message, cause, dict.getCode(), scenario);
    }

    public DatabaseException(Throwable cause, String scenario) {
        super(cause, dict.getCode(), scenario);
    }

    public DatabaseException(String message, Throwable cause,
                             boolean enableSuppression, boolean writableStackTrace,
                             String scenario) {
        super(message, cause, enableSuppression, writableStackTrace, dict.getCode(), scenario);
    }

    @Override
    public BaseFault convert() {
        com.tfkfan.webservices.types.DatabaseException fault = new com.tfkfan.webservices.types.DatabaseException();
        fault.setScenario(getScenario());
        fault.setMessage(getMessage());
        return fault;
    }
}
