package com.crm.graduation.crmsystem.exception;

public class CommonExecption extends Exception {
    private static final long serialVersionUID = -7540614225262703525L;
    private String exceptionCode;

    public CommonExecption(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public CommonExecption(String exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public CommonExecption(String exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionCode() {
        return this.exceptionCode;
    }
}
