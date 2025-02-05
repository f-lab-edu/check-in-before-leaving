package com.example.checkinrequestMS.HelpAPI.application.service.help.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpSelectApplication {

    private final CheckInService checkInService;
    private final LineUpService lineUpService;
    private final EtcService etcService;

    //@Cacheable(cacheNames = "help_searched", key = "'checkIn_' + #id")
    public CheckIn.DTO selectCheckIn(Long id) {
        return checkInService.findOne(id);
    }

    @Cacheable(cacheNames = "help_searched", key = "'lineUp_' + #id")
    public LineUp.DTO selectLineUp(Long id) {
        return lineUpService.findLineUp(id);
    }

    @Cacheable(cacheNames = "help_searched", key = "'etc_' + #id")
    public Etc.DTO selectEtc(Long id) {
        return etcService.findOne(id);
    }
}
