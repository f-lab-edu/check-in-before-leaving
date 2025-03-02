package com.company.checkin.place.domain.model.place.service;

import com.company.checkin.place.domain.model.place.Place;
import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.infra.adapter.db.PlaceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.company.checkin.place.domain.exceptions.place.PlaceErrorCode.ALREADY_REGISTERED_PLACE;

@Service
@RequiredArgsConstructor
public class PlaceWriteService {

    private final PlaceJPARepository placeJPARepository;

    @Transactional
    public void registerPlace(Place place) {
        placeJPARepository.findByPlaceName(place.getPlaceName())
                .ifPresent(p -> {
                    throw new PlaceException(ALREADY_REGISTERED_PLACE);
                });
        System.out.println("PLACE SERVICE");
        System.out.println(place.getId());
        placeJPARepository.save(place);
    }
}
