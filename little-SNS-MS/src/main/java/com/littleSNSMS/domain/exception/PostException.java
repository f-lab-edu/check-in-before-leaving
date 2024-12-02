package com.littleSNSMS.domain.exception;

public class PostException extends RuntimeException {
    public static String POST_NOT_YET_EXIST = "아직 포스트 되지 않았습니다.";
    public static String NOT_POSTING_REQUEST = "포스트 등록 요청이 아닙니다.";

    public static String NO_CONTENT_VALUE = "포스트 내용이 없습니다.";
    public static String NO_OWNER_VALUE = "포스트 소유자가 없습니다.";
    public static String NO_POST_ID_VALUE = "포스트 아이디가 없습니다.";
    public static String NO_CREATED_AT_VALUE = "포스트 생성일이 없습니다.";
    public static String NO_UPDATED_AT_VALUE = "포스트 수정일이 없습니다.";
    public static String NO_LIKES_VALUE = "포스트 좋아요 리스트가 없습니다.";

    public static String NO_MEMBER_ID_VALUE = "멤버 아이디가 없습니다.";
    public static String NO_MEMBER_EMAIL_VALUE = "멤버 이메일이 없습니다.";


    public PostException(String message) {
        super(message);
    }

}
