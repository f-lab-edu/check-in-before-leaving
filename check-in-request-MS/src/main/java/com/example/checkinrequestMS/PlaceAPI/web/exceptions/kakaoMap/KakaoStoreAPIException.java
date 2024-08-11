package com.example.checkinrequestMS.PlaceAPI.web.exceptions.kakaoMap;


import com.example.checkinrequestMS.common.exception.types.DomainException;
import com.example.checkinrequestMS.common.exception.types.WebException;

public class KakaoStoreAPIException extends WebException {

    public KakaoStoreAPIException(KakaoStoreAPIErrorCode errorCode) {
        super(errorCode.getDetail());
    }
    public KakaoStoreAPIException(KakaoStoreAPIErrorCode errorCode, String errorMessage){
        super(errorCode.getDetail() + errorMessage);
    }



}
