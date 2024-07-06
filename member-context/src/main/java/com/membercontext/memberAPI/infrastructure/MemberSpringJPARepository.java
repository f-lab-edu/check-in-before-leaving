package com.membercontext.memberAPI.infrastructure;

import com.membercontext.memberAPI.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSpringJPARepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
