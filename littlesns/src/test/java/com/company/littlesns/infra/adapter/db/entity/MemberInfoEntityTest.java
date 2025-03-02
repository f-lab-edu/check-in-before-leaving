package com.company.littlesns.infra.adapter.db.entity;

import com.company.littlesns.domain.model.post.LikeMemberInfo;
import com.company.littlesns.infra.adapter.db.entity.post.LikeMemberInfoEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberInfoEntityTest {

    @Test
    void of() {
        // Given
        Long postId = 1L;
        LikeMemberInfo memberInfo = new LikeMemberInfo("testId", "testEmail");

        // When
        LikeMemberInfoEntity sut = LikeMemberInfoEntity.of(postId, memberInfo);

        // Then
        assertEquals(memberInfo.getMemberId(), sut.getMemberId());
        assertEquals(memberInfo.getMemberEmail(), sut.getMemberEmail());
    }
}