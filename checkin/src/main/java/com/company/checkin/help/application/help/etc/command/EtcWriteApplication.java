package com.company.checkin.help.application.help.etc.command;

import com.company.checkin.help.domain.model.help.etc.EtcService;

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
