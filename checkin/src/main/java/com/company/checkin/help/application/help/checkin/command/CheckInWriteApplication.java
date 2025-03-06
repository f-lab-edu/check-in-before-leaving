package com.company.checkin.help.application.help.checkin.command;

import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.common.aop.testtime.TestTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckInWriteApplication {

    private final CheckInService checkInService;

    @Transactional
    @TestTime
    public Long register(@NonNull CheckInService.Creation dto) {
        return checkInService.register(dto).getId();
    }

    @Transactional
    //@CachePut(cacheNames = "help_searched", key = "'checkIn_' + #result")
    public CheckIn.DTO update(@NonNull CheckInService.Update dto) {
        return checkInService.update(dto);
    }

}
