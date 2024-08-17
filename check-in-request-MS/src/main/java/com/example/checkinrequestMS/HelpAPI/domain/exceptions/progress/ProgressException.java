package com.example.checkinrequestMS.HelpAPI.domain.exceptions.progress;

import com.example.checkinrequestMS.common.exception.types.DomainException;

public class ProgressException extends DomainException {

        public ProgressException(ProgressErrorCode errorCode) {
            super(errorCode.getDetail());
        }
}
