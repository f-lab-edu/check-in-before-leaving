package com.company.member.web.controller.response;

import com.company.member.common.fixture.domain.MemberFixture;
import com.company.member.domain.model.member.Member;
import com.company.member.web.controller.member.SignUpController;
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