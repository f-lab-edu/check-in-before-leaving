package com.littleSNSMS.infra.db.repository;

import com.littleSNSMS.infra.db.entities.PostEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PostR2dbcRepository extends R2dbcRepository<PostEntity, Long> {

//    @Query("INSERT INTO member_info (member_id, member_email) VALUES (:memberId, :memberEmail) RETURNING *")
//    MemberInfoEntity like(Long postId, MemberInfoEntity memberInfoEntity);
}

