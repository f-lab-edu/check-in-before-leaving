package com.example.checkinrequestMS.HelpAPI.application.service.help.write;


import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EtcWriteService {

    private final HelpDBAdapter helpDBAdapter;

    @Transactional
    public Long registerEtc(EtcRegisterDTO dto) {
        Etc etc = Etc.of(dto, Progress.DEFAULT);
        return helpDBAdapter.save(etc);
    }
}
