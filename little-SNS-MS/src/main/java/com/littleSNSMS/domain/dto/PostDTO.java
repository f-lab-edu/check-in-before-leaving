package com.littleSNSMS.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
public class PostDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
    @RequiredArgsConstructor
    public static final class MemberInfoDTO {
        private final String memberId;
        private final String memberName;
    }

    @Getter
    @NoArgsConstructor(force = true)
    @RequiredArgsConstructor
    public static final class Create {
        private final String content;
        private final MemberInfoDTO owner;

        public static Create of(String content, String memberId, String memberName) {
            return new Create(content, new MemberInfoDTO(memberId, memberName));
        }
    }

    @Getter
    @NoArgsConstructor(force = true)
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Update {
        //todo: 이후 이 부분도 로직 개발
        private final long id;
        private final String content;
        private final MemberInfoDTO owner;

        public static Update of(Long id, String content, String memberId, String memberName) {
            return new Update(id, content, new MemberInfoDTO(memberId, memberName));
        }
    }

    public static class Return {
        //todo: 이후 이 로직 추가 개발
        private Long id;
        private String content;
        private Long memberId;
        private String memberName;
    }

}
