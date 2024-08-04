package com.example.checkinrequestMS.PlaceAPI.common.exception;



public class DomainException extends RuntimeException{

    public DomainException(String errorMessage) {
        super(errorMessage);
    }
}
