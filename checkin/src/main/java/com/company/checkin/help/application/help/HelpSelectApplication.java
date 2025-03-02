package com.company.checkin.help.application.help;

import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.domain.model.help.etc.Etc;
import com.company.checkin.help.domain.model.help.etc.EtcService;
import com.company.checkin.help.domain.model.help.lineup.LineUp;
import com.company.checkin.help.domain.model.help.lineup.LineUpService;
import lombok.NonNull;
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
    public CheckIn.DTO selectCheckIn(@NonNull Long id) {
        return checkInService.findOne(id);
    }

    @Cacheable(cacheNames = "help_searched", key = "'lineUp_' + #id")
    public LineUp.DTO selectLineUp(@NonNull Long id) {
        return lineUpService.findOne(id);
    }

    @Cacheable(cacheNames = "help_searched", key = "'etc_' + #id")
    public Etc.DTO selectEtc(@NonNull Long id) {
        return etcService.findOne(id);

    }
}
