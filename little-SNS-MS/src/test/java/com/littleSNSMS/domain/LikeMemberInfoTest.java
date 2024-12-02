package com.littleSNSMS.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeMemberInfoTest {

    @Test
    void memberInfo() {
        String memberId = "testUUID";
        String memberName = "testName";

        LikeMemberInfo memberInfo = new LikeMemberInfo(memberId, memberName);

        assertEquals(memberId, memberInfo.getMemberId());
        assertEquals(memberName, memberInfo.getMemberEmail());
    }

}