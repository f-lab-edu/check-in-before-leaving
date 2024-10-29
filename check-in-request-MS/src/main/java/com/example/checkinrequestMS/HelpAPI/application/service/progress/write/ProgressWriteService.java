package com.example.checkinrequestMS.HelpAPI.application.service.progress.write;


import com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProgressWriteService {

    private final HelpDBAdapter helpDBAdapter;

    @Transactional
    public Long registerHelper(Long helpId, Long helperId) {
        Help help = helpDBAdapter.findById(helpId);
        help.start(helperId);

        return helpDBAdapter.save(help);
    }

    //사진파일저장, 인증 변화.

}
