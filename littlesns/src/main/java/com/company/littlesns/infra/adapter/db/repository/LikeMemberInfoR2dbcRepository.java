package com.company.littlesns.infra.adapter.db.repository;

import com.company.littlesns.infra.adapter.db.entity.post.LikeMemberInfoEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface LikeMemberInfoR2dbcRepository extends R2dbcRepository<LikeMemberInfoEntity, Long> {
}
