package com.membercontext.memberAPI.domain.entity;

import com.membercontext.memberAPI.web.controller.form.SignUpForm;
import com.membercontext.memberAPI.web.controller.form.UpdateForm;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE) //Todo: protected가 맞을지?
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name="member_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String phone;

    private String location; //동단위 위치.

    private Boolean isLocationServiceEnabled;

    private Long point;

    //Fixme 테스트: 도메인의 경우 서비스에서 사용되는데 따로 보통 테스트를 만드는지 궁금합니다.
    public void update(Member updatingMember) {
        this.email = updatingMember.getEmail();
        this.password = updatingMember.getPassword();
        this.name = updatingMember.getName();
        this.phone = updatingMember.getPhone();
        this.location = updatingMember.getLocation();
        this.isLocationServiceEnabled = updatingMember.getIsLocationServiceEnabled();
        this.point = updatingMember.getPoint();
    }

    public static Member from(SignUpForm form){
        return Member.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .name(form.getName())
                .phone(form.getPhone())
                .location(form.getLocation())
                .isLocationServiceEnabled(form.getIsLocationServiceEnabled())
                .point(form.getPoint())
                .build();

    }
    public static Member from(UpdateForm form){
        return Member.builder()
                .id(form.getId())
                .email(form.getEmail())
                .password(form.getPassword())
                .name(form.getName())
                .phone(form.getPhone())
                .location(form.getLocation())
                .isLocationServiceEnabled(form.getIsLocationServiceEnabled())
                .point(form.getPoint())
                .build();
    }

}
