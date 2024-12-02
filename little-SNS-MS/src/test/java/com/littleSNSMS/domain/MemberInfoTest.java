package com.littleSNSMS.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberInfoTest {

    @Test
    void memberInfo() {
        String memberId = "testUUID";
        String memberName = "testName";

        MemberInfo memberInfo = new MemberInfo(memberId, memberName);

        assertEquals(memberId, memberInfo.getMemberId());
        assertEquals(memberName, memberInfo.getMemberEmail());
    }

}