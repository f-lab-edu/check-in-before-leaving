package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.web.controller.form.SignUpForm;
import com.membercontext.memberAPI.web.controller.form.TrackForm;
import com.membercontext.memberAPI.web.controller.form.UpdateForm;
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

    private String location; //동단위 위치.

    private boolean locationServiceEnabled; //이후 변경

    @Embedded
    @Builder.Default
    private MemberLocation memberLocation = MemberLocation.UNKNOWN;

    private Long point;

    public void encryptPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public void updateLocation(TrackForm form) {
        if (!this.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        MemberLocation memberLocation = this.memberLocation;
        memberLocation.addLocation(form.getLatitude(), form.getLongitude(), form.getTimestamp());
    }

    public void update(Member updatingMember) {
        this.email = updatingMember.getEmail();
        this.password = updatingMember.getPassword();
        this.name = updatingMember.getName();
        this.phone = updatingMember.getPhone();
        this.location = updatingMember.getLocation();
        this.locationServiceEnabled = updatingMember.isLocationServiceEnabled();
        this.point = updatingMember.getPoint();
    }

    public static Member from(SignUpForm form) {
        return Member.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .name(form.getName())
                .phone(form.getPhone())
                .location(form.getLocation())
                .locationServiceEnabled(form.getIsLocationServiceEnabled())
                .point(form.getPoint())
                .build();

    }

    public static Member from(UpdateForm form) {
        return Member.builder()
                .id(form.getId())
                .email(form.getEmail())
                .password(form.getPassword())
                .name(form.getName())
                .phone(form.getPhone())
                .location(form.getLocation())
                .locationServiceEnabled(form.getIsLocationServiceEnabled())
                .point(form.getPoint())
                .build();
    }

    public void enableLocationAlarm(String token) {
        if (!this.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        MemberLocation memberLocation = this.memberLocation;
        memberLocation.addFCMToken(token);
    }
}
