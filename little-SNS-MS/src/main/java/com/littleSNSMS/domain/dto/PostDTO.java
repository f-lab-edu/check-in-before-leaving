package com.littleSNSMS.domain.dto;

import lombok.*;

@Getter
public class PostDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
    @RequiredArgsConstructor
    public static final class MemberInfoDTO {
        private final String memberId;
        private final String memberEmail;

    }

    @Getter
    @NoArgsConstructor(force = true)
    @RequiredArgsConstructor
    public static final class Create {
        private final String content;
        private final MemberInfoDTO owner; //todo: 사실상 Like

        public static Create of(String content, String memberId, String memberEmail) {
            return new Create(content, new MemberInfoDTO(memberId, memberEmail));
        }
    }

}
