package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class LineUpWriteService {

    private final HelpDBAdapter helpDBAdapter;
    private final PlaceJPARepository placeRepository;

    @Transactional
    public Long registerLineUp(LineUpRegisterDTO dto) {
        //check: change ID
        String idToSearch = dto.getPlaceId();
        if (idToSearch.contains("DB")) {
            idToSearch = idToSearch.replaceAll("[^0-9]", "");
        }
        Place place = placeRepository.findById(Long.parseLong(idToSearch))
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        LineUp lineUp = LineUp.of(dto, place, Progress.DEFAULT);

        return helpDBAdapter.save(lineUp);
    }


}
