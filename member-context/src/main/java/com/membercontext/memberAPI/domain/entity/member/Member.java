package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.memberAPI.application.exception.member.MemberErrorCode;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.web.controller.SignUpController;
import com.membercontext.memberAPI.web.controller.TrackController;

import jakarta.persistence.*;
import lombok.*;


import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;
import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.NOT_EXITING_USER;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    public static final String MEMBER_ID = "member_id";

    @Id
    @Column(name = MEMBER_ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String phone;

    private String address; //동단위 위치.

    private boolean locationServiceEnabled; //이후 변경

    @Embedded
    @Builder.Default
    private MemberLocation memberLocation = MemberLocation.UNKNOWN;

    private Long point;


    //CRUD
    //SignUp
    public Member signUp(JavaCryptoUtil encryption) {
        this.encryptPassword(encryption);
        return this;
    }

    private void encryptPassword(JavaCryptoUtil javaCryptoUtil) {
        String encryptedPassword = javaCryptoUtil.encrypt(this.password);
        this.password = encryptedPassword;
    }

    public static Member from(SignUpController.SignUpRequest req) {
        return Member.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .name(req.getName())
                .phone(req.getPhone())
                .address(req.getAddress())
                .locationServiceEnabled(req.getIsLocationServiceEnabled())
                .point(req.getPoint())
                .build();
    }

    // Update
    public void update(Member updatingMember) {
        this.email = updatingMember.getEmail();
        this.password = updatingMember.getPassword();
        this.name = updatingMember.getName();
        this.phone = updatingMember.getPhone();
        this.address = updatingMember.getAddress();
        this.locationServiceEnabled = updatingMember.isLocationServiceEnabled();
        this.point = updatingMember.getPoint();
    }

    public static Member from(SignUpController.UpdateRequest req) {
        return Member.builder()
                .id(req.getId())
                .email(req.getEmail())
                .password(req.getPassword())
                .name(req.getName())
                .phone(req.getPhone())
                .address(req.getLocation())
                .locationServiceEnabled(req.getIsLocationServiceEnabled())
                .point(req.getPoint())
                .build();
    }

    //BUSINESS LOGICS
    //LogIn
    public Member logIn(JavaCryptoUtil encryption, String password) {
        return this.checkPassword(encryption, password);
    }

    private Member checkPassword(JavaCryptoUtil encryption, String password) {
        if (this.password.equals(encryption.encrypt(password))) {
            return this;
        } else {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }
    }

    // Location Tracking
    public void startLocationTracking(TrackController.TrackRequest form) {
        if (!this.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        this.memberLocation = this.memberLocation.addLocation(form.getLatitude(), form.getLongitude(), form.getTimestamp());
    }

    // Push Alarm
    public void enablePushAlarm(String token) {
        if (!this.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        this.memberLocation = this.memberLocation.addFCMToken(token);
    }


}
