package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;

@Embeddable
@Getter
public class MemberLocation {

    public static MemberLocation UNKNOWN = new MemberLocation(0, 0, LocalDateTime.now(), "NOT_AVAILABLE");

    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
    private String fcmToken;


    public MemberLocation(double i, double i1, LocalDateTime now, String fcmToken) {
        this.latitude = i;
        this.longitude = i1;
        this.timestamp = now;
        this.fcmToken = fcmToken;
    }

    protected MemberLocation() {
    }

    public void addLocation(double latitude, double longitude, LocalDateTime timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public void addFCMToken(String token) {
        this.fcmToken = token;
    }

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
