package com.example.checkinrequestMS.HelpAPI.infra.exceptions.jpa;

import com.example.checkinrequestMS.common.exception.types.InfraException;

public class JPAException extends InfraException {

    public JPAException(JPAErrorCode error) {
        super(error.getDetail());
    }

    public JPAException(JPAErrorCode error, Exception e) {
        super(error.getDetail(), e);
    }
}
