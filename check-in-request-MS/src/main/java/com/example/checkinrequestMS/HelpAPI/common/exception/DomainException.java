package com.example.checkinrequestMS.HelpAPI.common.exception;



public class DomainException extends RuntimeException{

    public DomainException(String errorMessage) {
        super(errorMessage);
    }
}
