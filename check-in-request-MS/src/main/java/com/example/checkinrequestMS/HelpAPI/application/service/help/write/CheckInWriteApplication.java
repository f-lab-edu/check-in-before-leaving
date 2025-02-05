package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.common.aop.testTime.TestTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckInWriteApplication {

    private final CheckInService checkInService;

    @Transactional
    @TestTime
    public Long register(@NonNull CheckInService.Registration dto) {
        return checkInService.register(dto).getId();
    }

    @Transactional
    @CachePut(cacheNames = "help_searched", key = "'checkIn_' + #result")
    public CheckIn.DTO update(@NonNull CheckInService.Update dto) {
        return checkInService.update(dto);
    }

}
