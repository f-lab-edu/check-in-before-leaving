package com.example.checkinrequestMS.HelpAPI.domain.service.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.CheckInJPARepository;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class CheckInCRUDService {

    private final CheckInJPARepository checkInJPARepository;
    private final PlaceRepository placeRepository;

    public void registerCheckIn(CheckIn checkIn) {
        // check: CheckIn 중복 허용. ID로 구분. 사진으로 인증하며 여러 인증이 있는 경우 최신 사진을 전체 공개.
        Place place = placeRepository.findById(checkIn.getPlaceId())
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        checkIn.setCheckInTitle(place);

        checkInJPARepository.save(checkIn);
    }
}
