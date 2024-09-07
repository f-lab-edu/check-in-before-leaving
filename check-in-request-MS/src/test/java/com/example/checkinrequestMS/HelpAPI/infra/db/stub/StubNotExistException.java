package com.example.checkinrequestMS.HelpAPI.infra.db.stub;

public class StubNotExistException extends RuntimeException{

    public StubNotExistException(String message) {
        super(message);
    }
}
