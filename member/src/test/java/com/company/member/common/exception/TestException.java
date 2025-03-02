package com.company.member.common.exception;

public class TestException extends RuntimeException {

    public static String NOT_ALLOWED = "Not Allowed";
    public static String NOT_FOUND = "Not Found";

    public TestException(String message) {
        super(message);
    }
}
