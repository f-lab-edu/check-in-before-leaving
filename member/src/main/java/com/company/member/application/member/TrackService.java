package com.company.member.application.member;


import com.company.member.domain.model.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final MemberService memberService;

    @Transactional
    public void startLocationTracking(MemberService.Track request, String memberId) {
        memberService.startLocationTracking(memberId, request);
    }

    @Transactional
    public void enablePushAlarm(String token, String memberId) {
        memberService.enablePushAlarm(token, memberId);
    }
}
