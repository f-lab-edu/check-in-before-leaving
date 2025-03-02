package com.company.checkin.help.domain.exceptions;

public class ProgressException extends RuntimeException {

    public static final String NOT_ONGOING = "진행 중인 도움이 아닙니다.";
    public static final String NOT_AUTHENTICATED = "인증되지 않은 도움은 승인할 수 없습니다.";

    public ProgressException(String message) {
        super(message);
    }
}
