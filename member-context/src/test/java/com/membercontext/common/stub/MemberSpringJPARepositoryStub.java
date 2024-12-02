package com.membercontext.common.stub;

import com.membercontext.common.exception.NoStubException;
import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.application.exception.member.MemberErrorCode;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.db.jpa.member.MemberSpringJPARepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class MemberSpringJPARepositoryStub implements MemberSpringJPARepository {

    Set<Member> members = new HashSet<>();

    @Override
    public <S extends Member> S save(S entity) {
        if (members.stream().anyMatch(member -> member.getEmail().equals(entity.getEmail()))) {
            throw new MemberException(MemberErrorCode.ALREADY_REGISTERED_USER);
        } else if (members.stream().anyMatch(member -> member.getId().equals(entity.getId()))) {
            throw new MemberException(MemberErrorCode.ALREADY_REGISTERED_USER);
        }
        UUID uuid = UUID.randomUUID();
        Member member = MemberFixture.createMemberToSave(uuid.toString(), entity);
        members.add(member);

        return (S) members.stream().filter(member1 -> member1.getId().equals(member.getId())).findFirst().get();
    }

    @Override
    public Optional<Member> findById(String s) {
        return members.stream().filter(member -> member.getId().equals(s)).findFirst();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return members.stream().filter(member -> member.getEmail().equals(email)).findFirst();
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

    @Override
    public void flush() {
        throw new NoStubException();
    }

    @Override
    public <S extends Member> S saveAndFlush(S entity) {
        throw new NoStubException();
    }

    @Override
    public <S extends Member> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new NoStubException();
    }

    @Override
    public void deleteAllInBatch(Iterable<Member> entities) {
        throw new NoStubException();
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {
        throw new NoStubException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new NoStubException();
    }

    @Override
    public Member getOne(String s) {
        throw new NoStubException();
    }

    @Override
    public Member getById(String s) {
        throw new NoStubException();
    }

    @Override
    public Member getReferenceById(String s) {
        throw new NoStubException();
    }

    @Override
    public <S extends Member> Optional<S> findOne(Example<S> example) {
        throw new NoStubException();
    }

    @Override
    public <S extends Member> List<S> findAll(Example<S> example) {
        throw new NoStubException();
    }

    @Override
    public <S extends Member> List<S> findAll(Example<S> example, Sort sort) {
        throw new NoStubException();
    }

    @Override
    public <S extends Member> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new NoStubException();
    }

    @Override
    public <S extends Member> long count(Example<S> example) {
        throw new NoStubException();
    }

    @Override
    public <S extends Member> boolean exists(Example<S> example) {
        throw new NoStubException();
    }

    @Override
    public <S extends Member, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new NoStubException();
    }


    @Override
    public <S extends Member> List<S> saveAll(Iterable<S> entities) {
        throw new NoStubException();
    }


    @Override
    public boolean existsById(String s) {
        throw new NoStubException();
    }

    @Override
    public List<Member> findAll() {
        throw new NoStubException();
    }

    @Override
    public List<Member> findAllById(Iterable<String> strings) {
        throw new NoStubException();
    }

    @Override
    public long count() {
        throw new NoStubException();
    }

    @Override
    public void deleteById(String s) {
        throw new NoStubException();
    }

    @Override
    public void delete(Member entity) {
        members.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        throw new NoStubException();
    }

    @Override
    public void deleteAll(Iterable<? extends Member> entities) {
        throw new NoStubException();
    }

    @Override
    public void deleteAll() {
        throw new NoStubException();
    }

    @Override
    public List<Member> findAll(Sort sort) {
        throw new NoStubException();
    }

    @Override
    public Page<Member> findAll(Pageable pageable) {
        throw new NoStubException();
    }
}
