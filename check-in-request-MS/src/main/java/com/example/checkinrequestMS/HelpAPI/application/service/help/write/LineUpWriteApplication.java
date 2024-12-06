package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineUpWriteApplication {

    private final LineUpService lineUpService;

    public Long registerLineUp(LineUpService.Registration dto) {
        return lineUpService.registerLineUp(dto);
    }
}
