package com.membercontext.memberAPI.application.service;


import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.db.jpa.member.MemberJPARepository;
import com.membercontext.memberAPI.web.controller.form.TrackForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final MemberJPARepository memberRepository;

    @Transactional
    public void saveCurrentLocation(TrackForm form, String memberId) {

        Member member = memberRepository.findById(memberId);
        member.updateLocation(form);

    }

}
