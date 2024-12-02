package com.littleSNSMS.domain.exception;

public class PostException extends RuntimeException {
    public static String POST_NOT_YET_EXIST = "아직 포스트 되지 않았습니다.";

    public PostException(String message) {
        super(message);
    }

}
