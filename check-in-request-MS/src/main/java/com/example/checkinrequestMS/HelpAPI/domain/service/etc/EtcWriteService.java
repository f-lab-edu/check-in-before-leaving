package com.example.checkinrequestMS.HelpAPI.domain.service.etc;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.EtcJPARepository;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class EtcWriteService {
    private final EtcJPARepository etcJPARepository;
    private final PlaceJPARepository placeRepository;

    @Transactional
    public void registerEtc(Etc etc, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        etc.setPlace(place);

        etcJPARepository.save(etc);
    }
}
