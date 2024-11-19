package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import jakarta.persistence.Embeddable;
import lombok.*;

import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;
import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.NO_VALUE;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public final class MemberLocation {

    public static MemberLocation UNKNOWN = new MemberLocation(null, null, null, "NOT_AVAILABLE");

    @Nullable
    private final Double latitude;

    @Nullable
    private final Double longitude;

    @Nullable
    private final LocalDateTime timestamp;

    @Getter
    private final String fcmToken; //check: 엄밀히 말하면 여기는 다른 context

    @Builder(access = AccessLevel.PRIVATE)
    private MemberLocation(@Nullable Double latitude, @Nullable Double longitude, @Nullable LocalDateTime timestamp, @Nullable String fcmToken) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.fcmToken = fcmToken;
    }

    public MemberLocation addLocation(Double latitude, Double longitude, LocalDateTime timestamp) {
        if (latitude == null || longitude == null || timestamp == null) {
            throw new MemberException(NO_VALUE);
        }
        return new MemberLocation(latitude, longitude, timestamp, this.fcmToken);
    }

    public MemberLocation addFCMToken(String token) {
        if (token == null) {
            throw new MemberException(NO_VALUE);
        }
        return new MemberLocation(this.latitude, this.longitude, this.timestamp, token);
    }

    public Optional<LocalDateTime> getTimestamp(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return Optional.ofNullable(this.timestamp);
    }

    public Optional<Double> getLongitude(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return Optional.ofNullable(this.longitude);
    }

    public Optional<Double> getLatitude(Member member) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return Optional.ofNullable(this.latitude);
    }
    //todo: FCM token도 nullable 하도록 변경
//
//    public Optional<String> getFcmToken(Member member) {
//        return Optional.ofNullable(this.fcmToken);
//    }


}
