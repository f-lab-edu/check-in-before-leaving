package com.company.checkin.place.domain.exceptions.place;


import com.company.checkin.common.exception.types.DomainException;

public class PlaceException extends DomainException {


    public PlaceException(PlaceErrorCode errorCode) {
        super(errorCode.getDetail());
    }
}
