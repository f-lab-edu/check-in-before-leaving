package com.company.checkin.common.exception.types;

public class InfraException extends RuntimeException{

    public InfraException(String errorMessage) {
        super(errorMessage);
    }
    public InfraException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}
