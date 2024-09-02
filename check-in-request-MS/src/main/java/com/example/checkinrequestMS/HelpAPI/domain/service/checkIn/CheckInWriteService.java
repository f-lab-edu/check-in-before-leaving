package com.example.checkinrequestMS.HelpAPI.domain.service.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.CheckInJPARepository;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class CheckInWriteService {

    private final CheckInJPARepository checkInJPARepository;
    private final PlaceJPARepository placeRepository;

    public Long registerCheckIn(CheckIn checkIn, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        checkIn.setPlace(place);
        checkIn.setCheckInTitle(place);

        return checkInJPARepository.save(checkIn);
    }
}
