package com.example.checkinrequestMS.common.exception.types;



public class DomainException extends RuntimeException{
    public DomainException(String errorMessage) {
        super(errorMessage);
    }
}
