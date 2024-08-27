package com.example.checkinrequestMS.HelpAPI.domain.service.LineUp;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.LineUpJPARepository;
import com.example.checkinrequestMS.HelpAPI.infra.exceptions.JPAException;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.checkinrequestMS.HelpAPI.infra.exceptions.JPAErrorCode.ERROR_SAVING;
import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class LineUpCRUDService {

    private final LineUpJPARepository lineUpJPARepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void registerLineUp(LineUp lineUp) {
        Place place = placeRepository.findById(lineUp.getPlace().getId())
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        lineUp.setPlaceWithFullInfo(place);
        lineUp.setLineUpTitle(place);

        try {
            lineUpJPARepository.save(lineUp);
        } catch (Exception e) {
            throw new JPAException(ERROR_SAVING);
        }
    }


}
