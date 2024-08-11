package com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place;


import com.example.checkinrequestMS.common.exception.types.DomainException;

public class PlaceException extends DomainException {


    public PlaceException(PlaceErrorCode errorCode) {
        super(errorCode.getDetail());
    }
}
