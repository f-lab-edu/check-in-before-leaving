package com.example.checkinrequestMS.HelpAPI.application.service.help.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpSelectApplication {

    private final CheckInService checkInService;

    public CheckInService.CheckInSelected selectCheckIn(Long id) {
        return checkInService.findCheckIn(id);
    }
}
