package com.example.checkinrequestMS.HelpAPI.common.exception;



public class WebException extends RuntimeException{

    public WebException(String errorMessage){
        super(errorMessage);
    }
}
