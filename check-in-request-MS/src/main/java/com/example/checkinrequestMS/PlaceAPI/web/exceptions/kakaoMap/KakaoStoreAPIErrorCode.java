package com.example.checkinrequestMS.PlaceAPI.web.exceptions.kakaoMap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum KakaoStoreAPIErrorCode{


    URL_CONNECTION_ERROR("URL 연결 중 오류가 발생했습니다. URL 주소나 파라미터를 확인해 주세요."),
    URL_CONNECTION_ERROR_SPECIFIC("URL 연결 중 오류가 발생했습니다. 에러메시지: "),
    URL_PROTOCOL_EXCEPTION("적절한 HTTP 메서드를 선택해 주세요."),
    DATA_STREAM_EXCEPTION("데이터 스트림을 읽는 중 오류가 발생했습니다."),
    META_PARSING_ERROR("KakaoAPI의 Meta 정보 파싱 작업 중 오류가 발생했습니다."),
    FIRST_PAGE_PARSING_ERROR("첫 페이지의 가게 정보 파싱 작업 중 오류가 발생했습니다."),
    ADDITIONAL_PARSING_ERROR("추가 페이지의 가게 정보 파싱 작업 중 오류가 발생했습니다.");
    //API_CONNECTION_ERROR("API 연결 중 오류가 발생했습니다. 에러 메세지: ", connectionMessage),


    private final String detail;
}
