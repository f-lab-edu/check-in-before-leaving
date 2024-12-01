package com.littleSNSMS.infra.db.repository;

import com.littleSNSMS.infra.db.entities.LikeMemberInfoEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface LikeMemberInfoR2dbcRepository extends R2dbcRepository<LikeMemberInfoEntity, Long> {
}
