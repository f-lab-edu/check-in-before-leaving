package com.membercontext.memberAPI.application.service;


import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.service.MemberLocationService;
import com.membercontext.memberAPI.web.controller.TrackController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final MemberRepository memberRepository;
    private final MemberLocationService memberLocationService;

    @Transactional
    public void startLocationTracking(TrackController.TrackRequest request, String memberId) {
        Member member = memberRepository.findById(memberId);
        member.startLocationTracking(memberLocationService, request);
    }

    @Transactional
    public void enablePushAlarm(String token, String memberId) {
        Member member = memberRepository.findById(memberId);
        member.enablePushAlarm(memberLocationService, token);
    }
}
