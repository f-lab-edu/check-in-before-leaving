package com.company.checkin.help.application.help.lineup.command;

import com.company.checkin.help.domain.model.help.lineup.LineUpService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineUpWriteApplication {

    private final LineUpService lineUpService;

    public Long register(@NonNull LineUpService.Creation dto) {
        return lineUpService.register(dto).getId();

    }
}
