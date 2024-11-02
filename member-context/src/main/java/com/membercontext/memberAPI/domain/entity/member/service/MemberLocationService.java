package com.membercontext.memberAPI.domain.entity.member.service;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberLocation;
import com.membercontext.memberAPI.web.controller.TrackController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;

@Service
@RequiredArgsConstructor
public class MemberLocationService {

    public MemberLocation updateCurrentLatitudeAndLongitude(Member member, TrackController.TrackRequest request) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return member.getMemberLocation().addCurrentLatitudeAndLongitude(request.getLatitude(), request.getLongitude(), request.getTimestamp());
    }

    public MemberLocation addFCMToken(Member member, String token) {
        if (!member.isLocationServiceEnabled()) {
            throw new MemberException(LOCATION_SERVICE_NOT_PERMITTED);
        }
        return member.getMemberLocation().addFCMToken(token);
    }
}
