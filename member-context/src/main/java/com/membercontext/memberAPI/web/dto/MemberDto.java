package com.membercontext.memberAPI.web.dto;

import com.membercontext.memberAPI.domain.entity.Member;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//Fixme 캡슐화: 객체의 무분별한 생성을 막고 싶어 최대한 보수적으로 가져가고 싶은데 대신에 테스트할 객체를 만들기가 어려워 집니다.
//            많은 예제들이 Builder를 Public하게 사용하는 걸 보아 제가 테스트용으로 실제 객체를 만드는걸 너무 지양하고 있는건지 고민됩니다.

public class MemberDto {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String location;

    private Boolean isLocationServiceEnabled;

    private Long point;


    //Fixme 구조: DTO는 도메인에 넣을지 고민을 했지만 출력 형식은 인터페이스에서 변경해주는게 맞다고 생각하여 web 패키지에 위치 하였습니다.
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
