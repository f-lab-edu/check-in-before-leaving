package com.company.member.common.stub;

import com.company.member.common.fixture.domain.MemberFixture;
import com.company.member.domain.exceptions.member.MemberException;
import com.company.member.domain.model.member.MemberRepository;
import com.company.member.domain.model.member.Member;

import java.util.*;

import static com.company.member.domain.exceptions.member.MemberErrorCode.ALREADY_REGISTERED_USER;
import static com.company.member.domain.exceptions.member.MemberErrorCode.NOT_EXITING_USER;

public class MemberJPARepositoryStub implements MemberRepository {

    Set<Member> members = new HashSet<>();

    @Override
    public Member findByEmail(String email) {
        Optional<Member> memberFound = members.stream().filter(member -> member.getEmail().equals(email)).findFirst();
        System.out.println("hello");
        if (memberFound.isPresent()) {
            return memberFound.get();
        } else {
            throw new MemberException(NOT_EXITING_USER);
        }
    }

    @Override
    public Member findById(String id) {
        Optional<Member> memberFound = members.stream().filter(member -> member.getId().equals(id)).findFirst();
        if (memberFound.isPresent()) {
            return memberFound.get();
        } else {
            throw new MemberException(NOT_EXITING_USER);
        }
    }

    @Override
    public void delete(String id) {
        Member member = this.findById(id);
        members.remove(member);
    }

    @Override
    public String save(Member entity) {
        boolean alreadyExistCheck = members.stream().anyMatch(member -> member.getEmail().equals(entity.getEmail()));
        if (alreadyExistCheck) {
            throw new MemberException(ALREADY_REGISTERED_USER);
        }

        UUID uuid = UUID.randomUUID();
        Member member = MemberFixture.createMemberToSave(uuid.toString(), entity);
        members.add(member);
        return member.getId();
    }

    @Override
    public Member update(Member updatingMember) {
        Member member = findById(updatingMember.getId());
        members.remove(member);

        members.add(updatingMember);
        Member memberupdated = findById(updatingMember.getId());
        return memberupdated;
    }

    @Override
    public List<Member> findNearByMember(double x, double y, int radius) {
        return members.stream().filter(member ->
        {
            Optional<Double> longitude = member.getMemberLocation().getLongitude(member);
            Optional<Double> latitude = member.getMemberLocation().getLatitude(member);
            if (longitude.isPresent()) {
                return (Math.pow(longitude.get(), 2) + Math.pow(latitude.get(), 2))
                        <= Math.pow((radius / Math.pow(10, 5)), 2);
            } else {
                return false;
            }
        }).toList();
    }
}
