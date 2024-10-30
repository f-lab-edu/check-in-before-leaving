package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;

@Embeddable
@Getter
public final class MemberLocation {

    public static MemberLocation UNKNOWN = new MemberLocation(0, 0, LocalDateTime.now(), "NOT_AVAILABLE");

    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
    private String fcmToken; //check: 엄밀히 말하면 여기는 다른 context


    private MemberLocation(double i, double i1, LocalDateTime currentTime, String fcmToken) {
        this.latitude = i;
        this.longitude = i1;
        this.timestamp = currentTime;
        this.fcmToken = fcmToken;
    }

    protected MemberLocation() {
    }

    public MemberLocation addLocation(double latitude, double longitude, LocalDateTime timestamp) {
        return new MemberLocation(latitude, longitude, timestamp, this.fcmToken);
    }

    public MemberLocation addFCMToken(String token) {
        return new MemberLocation(this.latitude, this.longitude, this.timestamp, token);
    }

    public LocalDateTime getTimeStamp(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return this.timestamp;
    }

    public double getLongitude(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return longitude;
    }

    public double getLatitude(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return latitude;
    }

}
