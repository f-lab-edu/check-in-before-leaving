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
        assertEquals(memberEmail, memberInfoDTO.getMemberName());
    }

    @Test
    void create() {
        String content = "testContent";
        String memberId = "testUUID";
        String memberName = "testName";

        PostDTO.Create create = PostDTO.Create.of(content, memberId, memberName);

        assertEquals(content, create.getContent());
        assertEquals(memberId, create.getOwner().getMemberId());
        assertEquals(memberName, create.getOwner().getMemberName());
    }

    @Test
    void update() {
        Long id = 1L;
        String content = "testContent";
        String memberId = "testUUID";
        String memberName = "testName";

        PostDTO.Update update = PostDTO.Update.of(id, content, memberId, memberName);

        assertEquals(id, update.getId());
        assertEquals(content, update.getContent());
        assertEquals(memberId, update.getOwner().getMemberId());
        assertEquals(memberName, update.getOwner().getMemberName());
    }
}