package com.membercontext.memberAPI.application.service.MemberInfo;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.NOT_EXITING_USER;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final MemberRepository memberRepository;

    @Override
    public Member getMemberInfo(String id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            throw new MemberException(NOT_EXITING_USER);
        }
        return member;
    }
}
