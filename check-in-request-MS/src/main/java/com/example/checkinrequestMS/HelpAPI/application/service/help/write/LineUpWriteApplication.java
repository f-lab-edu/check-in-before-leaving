package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
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
