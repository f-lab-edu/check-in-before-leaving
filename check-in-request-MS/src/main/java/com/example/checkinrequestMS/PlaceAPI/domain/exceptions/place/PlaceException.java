package com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place;

import com.example.checkinrequestMS.PlaceAPI.common.exception.DomainException;


public class PlaceException extends DomainException {

    //fixme: Domain Exception이 있는데 이렇게 PlaceException을 만들어도 되는지 궁금합니다.
    //       제 생각에는 도메인 내부에도 여러 파트가 있을 수 있기에 Exception을 나누어서 구성하면
    //       어디서 난 에러인지 더 알기 쉬울 것같다고 생각해서 추가 하였습니다.
    public PlaceException(PlaceErrorCode errorCode) {
        super(errorCode.getDetail());
    }
}
