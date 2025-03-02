package com.company.littlesns.infra.exceptions;

public class PostEntityException extends RuntimeException {

    public static String NO_POST_ENTITY_ID_VALUE = "포스트 엔티티의 아이디가 없습니다.";
    public static String NO_POST_ENTITY_CONTENTS_VALUE = "포스트 엔티티의 내용이 없습니다.";
    public static String NO_POST_ENTITY_OWNER_ID_VALUE = "포스트 엔티티의 소유자 아이디가 없습니다.";
    public static String NO_POST_ENTITY_OWNER_EMAIL_VALUE = "포스트 엔티티의 소유자 이메일이 없습니다.";
    public static String NO_POST_ENTITY_CREATED_AT_VALUE = "포스트 엔티티의 생성일이 없습니다.";
    public static String NO_POST_ENTITY_UPDATED_AT_VALUE = "포스트 엔티티의 수정일이 없습니다.";
    public static String NO_POST_ENTITY_LIKES_VALUE = "포스트 엔티티의 좋아요 리스트가 없습니다.";

    public static String NO_POST_ID_VALUE_FOR_LIKE_MEMBER_INFO = "좋아요한 맴버 정보 엔티티에 포스트 아이디가 없습니다.";
    public static String NO_LIKE_MEMBER_INFO_ENTITY_MEMBER_ID_VALUE = "좋아요한 맴버 정보 엔티티에 좋아요한 맴버의 아이디가 없습니다.";
    public static String NO_LIKE_MEMBER_INFO_ENTITY_MEMBER_EMAIL_VALUE = "좋아요한 맴버 정보 엔티티에 좋아요한 맴버의 이메일 정보가 없습니다.";

    public static String NOT_POSTING_REQUEST = "포스팅 요청이 아닙니다.";


    //500
    public PostEntityException(String message) {
        super(message);
    }

}
