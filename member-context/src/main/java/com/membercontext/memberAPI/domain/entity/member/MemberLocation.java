package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;

@Embeddable
public class MemberLocation {

    public static MemberLocation UNKNOWN = new MemberLocation(0, 0, LocalDateTime.now());

    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;

    public MemberLocation(double i, double i1, LocalDateTime now) {
        this.latitude = i;
        this.longitude = i1;
        this.timestamp = now;
    }

    protected MemberLocation() {
    }

    //fixme: Progress 같이 MemberLocation도 위치정보 비 동의시 값이 없어야 하는 경우가 있습니다.
    //       일단 default 값인 UNKNOWN을 만들기는 했지만
    //       이 경우, 도메인의 모델과 인프라 계층을 Help처럼 분리해서 만드는게 좋다고 생각했습니다.
    //       (MemberWithLocation이란 모델을 만들어 Member와 구분되게 도메인에서 써도 좋을 것 같다고 생각했습니다.)
    //       JPA 객체로는 다양한 모델을 만들기 어려울것 같아 일단 값을 잘못쓰지 않도록
    //       Exception처리하고 Member를 Get때 인자로 넣도록 하여 일반적인 get 매서드가 아님을 강조 하였습니다.
    //       구조에 대한 변화는 조금 고민이 필요한것 같아 이 부분도 DDD공부하며 Help와 함께 이후에 변경하겠습니다.

    public LocalDateTime getTimeStamp(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return this.timestamp;
    }

    public Double getLongitude(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return longitude;
    }

    public Double getLatitude(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return latitude;
    }
}
