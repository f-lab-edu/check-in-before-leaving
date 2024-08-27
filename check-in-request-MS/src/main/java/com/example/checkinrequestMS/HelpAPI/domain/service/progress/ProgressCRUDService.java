package com.example.checkinrequestMS.HelpAPI.domain.service.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.HelpException;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.HelpJPARepository;
import com.example.checkinrequestMS.HelpAPI.infra.db.progress.ProgressJPARepository;
import com.example.checkinrequestMS.HelpAPI.infra.exceptions.JPAException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.HelpErrorCode.NO_HELP_INFO;
import static com.example.checkinrequestMS.HelpAPI.infra.exceptions.JPAErrorCode.ERROR_SAVING;

@Service
@RequiredArgsConstructor
public class ProgressCRUDService {

    private final ProgressJPARepository progressJPARepository;
    private final HelpJPARepository helpJPARepository;

    @Transactional
    public Progress registerProgress(Progress progress) {
        Help help = helpJPARepository.findById(progress.getHelp().getId()).orElseThrow(
                () -> new HelpException(NO_HELP_INFO)
        );
        progress.setHelp(help);

        try{
            return progressJPARepository.save(progress);
        }catch (Exception e) {
            throw new JPAException(ERROR_SAVING);
        }
    }

    //사진파일저장, 인증 변화.

}
