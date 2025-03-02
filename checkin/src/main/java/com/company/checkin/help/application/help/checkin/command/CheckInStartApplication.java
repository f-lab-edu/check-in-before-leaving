package com.company.checkin.help.application.help.checkin.command;

import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckInStartApplication {

    private final CheckInService checkInService;

    public Long startCheckIn(@NonNull CheckInService.Start dto) {
        return checkInService.start(dto).getId();
    }
}
