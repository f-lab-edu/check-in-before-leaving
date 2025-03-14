package com.company.checkin.help.infra.exceptions.db.jpa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JPAErrorCode {
    ERROR_SAVE("JPA 저장 작업 중 에러"),
    ERROR_UPDATE("JPA 업데이트 작업 중 에러"),
    ERROR_READ("JPA 조회 작업 중 에러");


    private final String detail;
}
