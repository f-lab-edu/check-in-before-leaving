package com.membercontext.memberAPI.application.service;


import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.TrackController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveCurrentLocation(TrackController.TrackRequest form, String memberId) {
        Member member = memberRepository.findById(memberId);
        member.updateLocation(form);
    }

    @Transactional
    public void saveToken(String token, String memberId) {
        Member member = memberRepository.findById(memberId);
        member.startLocationAlarm(token);
    }
}
