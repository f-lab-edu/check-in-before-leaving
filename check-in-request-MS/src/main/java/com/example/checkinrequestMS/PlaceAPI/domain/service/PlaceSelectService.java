package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class PlaceSelectService {
    private final PlaceJPARepository placeJPARepository;

    public Place selectPlaceDetail(String name) {
        return placeJPARepository.findByPlaceName(name)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
    }
}
