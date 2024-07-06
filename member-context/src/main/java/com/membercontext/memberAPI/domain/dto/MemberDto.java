package com.membercontext.memberAPI.domain.dto;

import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {

    private Long id;

    private String email;

    private String password;



}
