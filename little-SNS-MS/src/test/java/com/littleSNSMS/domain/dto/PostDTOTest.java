package com.littleSNSMS.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PostDTOTest {

    @Test
    void memberInfoDTO() {
        String memberId = "testUUID";
        String memberEmail = "testName";

        PostDTO.MemberInfoDTO memberInfoDTO = new PostDTO.MemberInfoDTO(memberId, memberEmail);

        assertEquals(memberId, memberInfoDTO.getMemberId());
        assertEquals(memberEmail, memberInfoDTO.getMemberEmail());
    }

    @Test
    void create() {
        String content = "testContent";
        String memberId = "testUUID";
        String memberName = "testName";

        PostDTO.Create create = PostDTO.Create.of(content, memberId, memberName);

        assertEquals(content, create.getContent());
        assertEquals(memberId, create.getOwner().getMemberId());
        assertEquals(memberName, create.getOwner().getMemberEmail());
    }
}