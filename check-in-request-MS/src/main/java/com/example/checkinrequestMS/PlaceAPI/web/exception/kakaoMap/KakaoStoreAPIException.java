package com.example.checkinrequestMS.PlaceAPI.web.exception.kakaoMap;

import com.example.checkinrequestMS.PlaceAPI.common.exception.WebException;

public class KakaoStoreAPIException extends WebException {

    public KakaoStoreAPIException(KakaoStoreAPIErrorCode errorCode) {
        super(errorCode.getDetail());
    }
    public KakaoStoreAPIException(KakaoStoreAPIErrorCode errorCode, String errorMessage){
        super(errorCode.getDetail() + errorMessage);
    }



}
