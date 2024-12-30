package com.example.checkinrequestMS.HelpAPI.application.service.help.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpSelectApplication {

    private final CheckInService checkInService;
    private final LineUpService lineUpService;
    private final EtcService etcService;

    @Cacheable(cacheNames = "help_searched", key = "'checkIn_' + #id")
    public CheckInService.CheckInSelected selectCheckIn(Long id) {
        return checkInService.findCheckIn(id);
    }

    @Cacheable(cacheNames = "help_searched", key = "'lineUp_' + #id")
    public LineUpService.LineUpSelected selectLineUp(Long id) {
        return lineUpService.findLineUp(id);
    }

    @Cacheable(cacheNames = "help_searched", key = "'etc_' + #id")
    public EtcService.EtcSelected selectEtc(Long id) {
        return etcService.findEtc(id);
    }
}
