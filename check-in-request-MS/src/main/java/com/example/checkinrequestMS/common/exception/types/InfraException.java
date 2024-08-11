package com.example.checkinrequestMS.common.exception.types;

public class InfraException extends RuntimeException{

    public InfraException(String errorMessage) {
        super(errorMessage);
    }
}
