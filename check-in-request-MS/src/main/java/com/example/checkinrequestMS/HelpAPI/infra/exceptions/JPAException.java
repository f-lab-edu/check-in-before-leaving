package com.example.checkinrequestMS.HelpAPI.infra.exceptions;

import com.example.checkinrequestMS.common.exception.types.InfraException;

public class JPAException extends InfraException {
    public JPAException(JPAErrorCode e) {
        super(e.getDetail());
    }
}
