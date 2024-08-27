package com.example.checkinrequestMS.HelpAPI.domain.service.etc;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.HelpException;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.EtcJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.HelpErrorCode.No_ETC_INFO;

@Service
@RequiredArgsConstructor
public class EtcSelectService {

    private final EtcJPARepository etcJPARepository;

    @Transactional
    public Etc selectEtc(Long id) {
        return etcJPARepository.findById(id).orElseThrow(
                () -> new HelpException(No_ETC_INFO)
        );

    }
}
