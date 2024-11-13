package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
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
    private final AlarmService alarmService;

    @Transactional
    public Long registerCheckIn(CheckInRegisterDTO dto) {
        String idToSearch = dto.getPlaceId();
        if (idToSearch.contains("DB")) {
            idToSearch = idToSearch.replaceAll("[^0-9]", "");
        }
        Place place = placeRepository.findById(Long.parseLong(idToSearch))
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO)); //check: DB외에도 처리 해야함.
        CheckIn checkIn = CheckIn.of(dto, place, Progress.DEFAULT);

        //alarmService.sendAlarmToUsersNearby(place.getId(), place.getX(), place.getY();
        alarmService.sendAlarmToUsersNearby(dto.getHelpRegisterId(), place);


        return helpDBAdapter.save(checkIn);
    }

}
