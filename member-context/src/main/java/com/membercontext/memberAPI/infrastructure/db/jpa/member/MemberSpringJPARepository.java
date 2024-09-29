package com.membercontext.memberAPI.infrastructure.db.jpa.member;

import com.membercontext.memberAPI.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSpringJPARepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);
}
