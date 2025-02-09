package com.example.checkinrequestMS.HelpAPI.application.service.progress;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgressStartApplication {

    private final CheckInService checkInService;

    public Long startCheckIn(@NonNull CheckInService.Start dto) {
        return checkInService.start(dto).getId();
    }
}
