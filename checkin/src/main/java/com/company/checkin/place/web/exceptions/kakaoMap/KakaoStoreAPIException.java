package com.company.checkin.place.web.exceptions.kakaoMap;


import com.company.checkin.common.exception.types.WebException;

public class KakaoStoreAPIException extends WebException {

    public KakaoStoreAPIException(KakaoStoreAPIErrorCode errorCode) {
        super(errorCode.getDetail());
    }

    public KakaoStoreAPIException(KakaoStoreAPIErrorCode errorCode, String errorMessage) {
        super(errorCode.getDetail() + errorMessage);
    }


}
