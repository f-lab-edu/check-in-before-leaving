package com.example.checkinrequestMS.common.exception.types;



public class WebException extends RuntimeException{

    public WebException(String errorMessage){
        super(errorMessage);
    }
}
