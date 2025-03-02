package com.company.checkin.help.domain.exceptions.help;

import com.company.checkin.common.exception.types.DomainException;


public class HelpException extends DomainException {

    public HelpException(HelpErrorCode errorCode) {
        super(errorCode.getDetail());
    }
}
