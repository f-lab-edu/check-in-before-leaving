package com.company.checkin.common.exception.types;



public class DomainException extends RuntimeException{
    public DomainException(String errorMessage) {
        super(errorMessage);
    }
}
