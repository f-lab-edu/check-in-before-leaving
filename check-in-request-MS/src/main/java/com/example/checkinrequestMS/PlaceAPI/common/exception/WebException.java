package com.example.checkinrequestMS.PlaceAPI.common.exception;



public class WebException extends RuntimeException{

    public WebException(String errorMessage){
        super(errorMessage);
    }
}
