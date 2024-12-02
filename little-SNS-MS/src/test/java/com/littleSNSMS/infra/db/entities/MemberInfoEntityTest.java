package com.littleSNSMS.infra.db.entities;

import com.littleSNSMS.domain.LikeMemberInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberInfoEntityTest {

    @Test
    void of() {
        // Given
        LikeMemberInfo memberInfo = new LikeMemberInfo("testId", "testEmail");

        // When
        LikeMemberInfoEntity sut = LikeMemberInfoEntity.of(memberInfo);

        // Then
        assertEquals(memberInfo.getMemberId(), sut.getMemberId());
        assertEquals(memberInfo.getMemberEmail(), sut.getMemberEmail());
    }
}