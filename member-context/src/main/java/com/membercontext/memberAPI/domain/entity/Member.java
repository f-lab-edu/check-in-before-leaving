package com.membercontext.memberAPI.domain.entity;

import com.membercontext.memberAPI.web.form.SignUpForm;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    private String phone; //TODO Q.Integer?

    private String location; //동단위 위치.

    private Boolean isLocationServiceEnabled;

    private Long point;

    @Builder
    public Member(String email, String password, String name, String phone, String location, Boolean isLocationServiceEnabled, Long point, Long id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.isLocationServiceEnabled = isLocationServiceEnabled;
        this.point = point;
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

}
