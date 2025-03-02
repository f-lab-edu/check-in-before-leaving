package com.company.member.domain.model.member;

import java.util.List;

public interface MemberRepository {
    Member findByEmail(String email);

    Member findById(String id);

    void delete(String id);

    String save(Member member);

    Member update(Member updatingMember);

    List<Member> findNearByMember(double x, double y, int radius);
}
