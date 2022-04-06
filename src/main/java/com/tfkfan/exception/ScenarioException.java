package com.tfkfan.exception;

/**
 * @author Baltser Artem tfkfan
 */
public abstract class ScenarioException extends BusinessException{
    private final String scenario;

    public ScenarioException(String code, String scenario) {
        super(code);
        this.scenario = scenario;
    }

    public ScenarioException(String message, String code, String scenario) {
        super(message, code);
        this.scenario = scenario;
    }

    public ScenarioException(ExceptionDictionary dictionary, String scenario) {
        super(dictionary);
        this.scenario = scenario;
    }

    public ScenarioException(ExceptionDictionary dictionary, String message, String scenario) {
        super(dictionary, message);
        this.scenario = scenario;
    }

    public ScenarioException(String message, Throwable cause, String code, String scenario) {
        super(message, cause, code);
        this.scenario = scenario;
    }

    public ScenarioException(Throwable cause, String code, String scenario) {
        super(cause, code);
        this.scenario = scenario;
    }

    public ScenarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String scenario) {
        super(message, cause, enableSuppression, writableStackTrace, code);
        this.scenario = scenario;
    }

    public String getScenario() {
        return scenario;
    }
}
