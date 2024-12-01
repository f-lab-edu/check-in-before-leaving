package com.membercontext.memberAPI.web.controller.response;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.SignUpController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberResponseTest {

    @Test
    void from() {
        // Given
        Member member = MemberFixture.createMemberWithId("UUID");

        //when
        SignUpController.MemberResponse memberResponse = SignUpController.MemberResponse.from(member);

        //then
        assertEquals(member.getId(), memberResponse.getId());
        assertEquals(member.getEmail(), memberResponse.getEmail());
        assertEquals(member.getName(), memberResponse.getName());
        assertEquals(member.getPhone(), memberResponse.getPhone());
        assertEquals(member.getAddress(), memberResponse.getAddress());
        assertEquals(member.getPoint(), memberResponse.getPoint());
    }

}