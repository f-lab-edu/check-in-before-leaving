package com.example.checkinrequestMS.HelpAPI.domain.service;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.HelpException;
import com.example.checkinrequestMS.HelpAPI.infra.HelpJPARepository;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.HelpErrorCode.CHECK_IN_REGISTER_FAILED;
import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class HelpRegisterService {

    private final HelpJPARepository helpJPARepository;
    private final PlaceRepository placeRepository;

    public void registerCheckIn(CheckIn checkIn) {
        // check: 중복 허용. ID로 구분. 사진으로 인증하며 여러 인증이 있는 경우 최신 사진을 전체 공개.
        Place place = placeRepository.findById(checkIn.getPlace().getId())
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        checkIn.setPlaceWithFullInfo(place);
        checkIn.setCheckInTitle(place);

        try {
            //fixme: 저번에도 궁금했는데 잊어버렸었네요. DB에서 에러가 생길 수 있다고 생각하는데
            //       예외처리를 한번 해주는게 좋지 않을까요?
            //       이렇게 하는 경우를 본적이 없어서 멘토님의 생각이 궁금합니다.
            //       이거는 repository에서 잡아야 하는가 싶기도 합니다.
            helpJPARepository.save(checkIn);
        } catch (Exception e) {
            throw new HelpException(CHECK_IN_REGISTER_FAILED);
        }
    }
}
