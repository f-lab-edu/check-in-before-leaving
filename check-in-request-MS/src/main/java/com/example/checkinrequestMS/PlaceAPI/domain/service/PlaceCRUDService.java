package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.ALREADY_REGISTERED_PLACE;

@Service
@RequiredArgsConstructor
public class PlaceCRUDService {

    private final PlaceJPARepository placeJPARepository;

    @Transactional
    public void registerPlace(Place place){
        placeJPARepository.findByPlaceName(place.getPlaceName())
                .ifPresent(p -> {
                    throw new PlaceException(ALREADY_REGISTERED_PLACE);
                });

        placeJPARepository.save(place);
    }
}
