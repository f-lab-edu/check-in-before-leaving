package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckInWriteApplication {

    private final CheckInService checkInService;

    @Transactional
    public Long registerCheckIn(CheckInService.Registration dto) {
        return checkInService.registerCheckIn(dto);
    }

}
