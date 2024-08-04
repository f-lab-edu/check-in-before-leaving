package com.example.checkinrequestMS.HelpAPI.domain.exceptions;

import com.example.checkinrequestMS.HelpAPI.common.exception.DomainException;


public class HelpException extends DomainException {

    public HelpException(HelpErrorCode errorCode) {
        super(errorCode.getDetail());
    }
}
