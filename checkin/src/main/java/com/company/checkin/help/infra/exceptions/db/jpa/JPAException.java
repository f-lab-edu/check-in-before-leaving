package com.company.checkin.help.infra.exceptions.db.jpa;

import com.company.checkin.common.exception.types.InfraException;

public class JPAException extends InfraException {

    public JPAException(JPAErrorCode error) {
        super(error.getDetail());
    }

    public JPAException(JPAErrorCode error, Exception e) {
        super(error.getDetail(), e);
    }
}
