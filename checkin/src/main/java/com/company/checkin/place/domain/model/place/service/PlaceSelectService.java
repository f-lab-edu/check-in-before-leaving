package com.company.checkin.place.domain.model.place.service;

import com.company.checkin.place.domain.model.place.Place;
import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.infra.adapter.db.PlaceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.company.checkin.place.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class PlaceSelectService {
    private final PlaceJPARepository placeJPARepository;

    public Place selectPlaceDetail(String name) {
        return placeJPARepository.findByPlaceName(name)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
    }
}
