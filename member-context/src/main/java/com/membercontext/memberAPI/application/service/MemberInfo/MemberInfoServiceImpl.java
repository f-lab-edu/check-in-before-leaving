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
        //fixme: 조회에 대한 책임도 응용계층에서 처리하게 되는지 궁금합니다. 이 로직을 Member에 넣기도 어려울 것 같아서요.
        if (member == null) {
            throw new MemberException(NOT_EXITING_USER);
        }
        return member;

    }
}
