package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.web.controller.TrackController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.NOT_EXITING_USER;

@Service
@RequiredArgsConstructor
public class MemberService {
    // Member와 같은 패키지에 있어야 함.
    private final MemberRepository memberRepository;
    private final JavaCryptoUtil javaCryptoUtil;

    public String signUp(Member member) {
        String encryptedPassword = javaCryptoUtil.encrypt(member.getPassword());
        member.encryptPassword(encryptedPassword);
        return memberRepository.save(member);
    }

    public Member update(Member updatingMember) {
        Member member = memberRepository.findById(updatingMember.getId());
        return member.update(updatingMember);
    }

    public void delete(String id) {
        memberRepository.delete(id);
    }

    //Read
    public Member findOneMember(String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            throw new MemberException(NOT_EXITING_USER);
        }
        return member;
    }

    //LogIn
    public Member logIn(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        return member.logIn(javaCryptoUtil, password);
    }

    //Track
    public void startLocationTracking(String memberId, TrackController.TrackRequest request) {
        Member member = memberRepository.findById(memberId);
        member.startLocationTracking(request);
    }

    public void enablePushAlarm(String token, String memberId) {
        Member member = memberRepository.findById(memberId);
        member.enablePushAlarm(token);
    }

    //Push Alarm
    public List<String> getNearByMemberTokens(double x, double y, int radius) {
        List<Member> membersToPush = memberRepository.findNearByMember(x, y, radius);
        if (membersToPush.isEmpty()) {
            return null;
        }
        return membersToPush.stream()
                .map(member -> member.getMemberLocation().getFcmToken())
                .toList();
    }


}
