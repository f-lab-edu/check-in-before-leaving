package com.membercontext.memberAPI.common.exception.types;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TechnicalException extends RuntimeException {

    private static String TECHNICAL_EXCEPTION_MESSAGE_DEFAULT = TechnicalException.class.getSimpleName() + " :";

    public TechnicalException(String message, Exception e) {
        super(message);
        log.error(TECHNICAL_EXCEPTION_MESSAGE_DEFAULT + message);
        e.printStackTrace();
    }
}
