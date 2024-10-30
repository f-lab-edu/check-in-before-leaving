package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class CheckInWriteService {

    private final HelpDBAdapter helpDBAdapter;
    private final PlaceJPARepository placeRepository;

    @Transactional
    public Long registerCheckIn(CheckInRegisterDTO dto) {
        Place place = placeRepository.findById(Long.parseLong(dto.getPlaceId()))
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        CheckIn<Created> checkIn = CheckIn.of(dto, place, Created.create());
        return helpDBAdapter.save(checkIn);
    }

}
