package com.company.member.common.stub;

import com.company.member.common.fixture.domain.MemberFixture;
import com.company.member.domain.exceptions.member.MemberErrorCode;
import com.company.member.domain.exceptions.member.MemberException;
import com.company.member.domain.model.member.MemberRepository;
import com.company.member.domain.model.member.Member;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class MemberRepositoryMockStub implements MemberRepository {

    private MemberRepository memberRepository;

    public MemberRepositoryMockStub() {
        this.memberRepository = spy(MemberRepository.class);
    }

    @Override
    public Member findByEmail(String email) {
        return null;
    }

    @Override
    public Member findById(String id) {
        UUID uuid = UUID.randomUUID();
        Member member = MemberFixture.createMemberWithId(uuid.toString());
        when(memberRepository.findById(id)).thenReturn(member);

        return memberRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        doNothing().when(memberRepository).delete(id);
        when(memberRepository.findById(id)).thenThrow(new MemberException(MemberErrorCode.NOT_EXITING_USER));
    }


    @Override
    public String save(Member member) {
        UUID uuid = UUID.randomUUID();
        when(memberRepository.save(member)).thenReturn(uuid.toString());

        return memberRepository.save(member);
    }

    @Override
    public Member update(Member updatingMember) {
        when(memberRepository.update(updatingMember)).thenReturn(updatingMember);
        return memberRepository.update(updatingMember);
    }

    @Override
    public List<Member> findNearByMember(double x, double y, int radius) {
        return List.of();
    }
}
