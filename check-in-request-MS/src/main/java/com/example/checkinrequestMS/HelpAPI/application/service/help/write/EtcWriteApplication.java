package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtcWriteApplication {

    private final EtcService etcService;

    public Long register(@NonNull EtcService.Creation dto) {
        return etcService.register(dto).getId();
    }
}
