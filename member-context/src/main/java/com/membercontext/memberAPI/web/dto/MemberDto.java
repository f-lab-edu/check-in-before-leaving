package com.membercontext.memberAPI.web.dto;

import com.membercontext.memberAPI.domain.entity.member.Member;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class MemberDto {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String location;

    private Boolean isLocationServiceEnabled;

    private Long point;


    public static MemberDto from(Member member){
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .location(member.getLocation())
                .isLocationServiceEnabled(member.getIsLocationServiceEnabled())
                .point(member.getPoint())
                .build();
    }



}
