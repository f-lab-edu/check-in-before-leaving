package com.company.frontserver.web.response;


public class ExceptionResponse {

    protected final String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    protected String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "message='" + message +
                '}';
    }

}
