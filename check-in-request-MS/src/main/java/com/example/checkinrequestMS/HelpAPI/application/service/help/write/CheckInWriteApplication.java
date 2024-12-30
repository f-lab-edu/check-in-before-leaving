package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.TestClass;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.common.aop.testTime.TestTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckInWriteApplication {

    private final CheckInService checkInService;

    @Transactional
    @TestTime
    public Long registerCheckIn(CheckInService.Registration dto) {
        return checkInService.registerCheckIn(dto);
    }

}
