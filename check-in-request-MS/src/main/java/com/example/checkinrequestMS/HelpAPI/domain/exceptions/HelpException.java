package com.example.checkinrequestMS.HelpAPI.domain.exceptions;

import com.example.checkinrequestMS.common.exception.types.DomainException;


public class HelpException extends DomainException {

    public HelpException(HelpErrorCode errorCode) {
        super(errorCode.getDetail());
    }
}
