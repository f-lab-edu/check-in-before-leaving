package com.membercontext.memberAPI.infrastructure.db.jpa.member;

import com.membercontext.memberAPI.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberSpringJPARepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT m from Member m WHERE (POWER(:x - m.memberLocation.longitude, 2) + POWER(:y - m.memberLocation.latitude, 2)) <= POWER(:radius/POWER(10,5), 2)")
    List<Member> findNearByMember(double x, double y, int radius);
}
