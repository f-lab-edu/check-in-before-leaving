package com.membercontext.memberAPI.repository;

import com.membercontext.memberAPI.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignUpJPARepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
