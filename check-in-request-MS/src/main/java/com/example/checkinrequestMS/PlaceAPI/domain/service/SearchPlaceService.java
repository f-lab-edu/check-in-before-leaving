package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoAPIStoreInfoSaver;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;


@Service
@RequiredArgsConstructor
@Slf4j
public class SearchPlaceService {

    private final PlaceRepository storeRepository;
    private final KakaoAPIStoreInfoSaver storeInfoBalancer;

    @Transactional
    public List<Place> searchWithKeyword(String query, double x, double y, int radius)  {

        storeInfoBalancer.balanceKeyWordSearch(query, x, y, radius);
        List<Place> places = storeRepository.getStoresByNameAndRadius(x, y, radius)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));

        return places;
    }

}
