package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.web.controller.SignUpController;
import com.membercontext.memberAPI.web.controller.TrackController;

import jakarta.persistence.*;
import lombok.*;


import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;

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
    private MemberLocation memberLocation = new MemberLocation().UNKNOWN;

    private Long point;

    public void encryptPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public void updateLocation(TrackController.TrackRequest form) {
        if (!this.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        this.memberLocation = this.memberLocation.addLocation(form.getLatitude(), form.getLongitude(), form.getTimestamp());
    }

    public void update(Member updatingMember) {
        this.email = updatingMember.getEmail();
        this.password = updatingMember.getPassword();
        this.name = updatingMember.getName();
        this.phone = updatingMember.getPhone();
        this.address = updatingMember.getAddress();
        this.locationServiceEnabled = updatingMember.isLocationServiceEnabled();
        this.point = updatingMember.getPoint();
    }

    public void startLocationAlarm(String token) {
        if (!this.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        this.memberLocation = this.memberLocation.addFCMToken(token);
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


}
