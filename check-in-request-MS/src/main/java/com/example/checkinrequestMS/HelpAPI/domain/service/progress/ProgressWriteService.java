package com.example.checkinrequestMS.HelpAPI.domain.service.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.progress.ProgressException;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.HelpJPARepository;
import com.example.checkinrequestMS.HelpAPI.infra.db.progress.ProgressJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode.NO_HELP_INFO;
import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.progress.ProgressErrorCode.NO_PROGRESS;

@Service
@RequiredArgsConstructor
public class ProgressWriteService {

    private final ProgressJPARepository progressJPARepository;
    private final HelpJPARepository helpJPARepository;

    @Transactional
    public Progress registerProgress(Progress progress, Long helpId) {
        Help help = helpJPARepository.findById(helpId).orElseThrow(
                () -> new HelpException(NO_HELP_INFO));
        progress.setHelp(help);
        progressJPARepository.save(progress);

        return progressJPARepository.findById(progress.getId()).orElseThrow(
                () -> new ProgressException(NO_PROGRESS));
    }
    //사진파일저장, 인증 변화.

}
