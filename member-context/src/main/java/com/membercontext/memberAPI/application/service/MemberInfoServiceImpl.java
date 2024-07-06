package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.exception.member.MemberErrorCode;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService{

    private final MemberRepository memberRepository;

    //Fixme 구조: Repository를 분리하니 예외처리를 어디서 해야 하는지 헷갈립니다.
    //           예외처리는 기술보다 비즈니스 로직 관련이라고 생각하여 도메인에 남겼습니다.
    @Override
    public Member getMemberInfo(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_EXITING_USER));
    }
}
