package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpRepository;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LineUpJPARepository implements LineUpRepository {

    private final LineUpSpringJPARepository lineUpSpringJPARepository;

    @Override
    public LineUp save(LineUp lineUp) {
        return lineUpSpringJPARepository.save(LineUpEntity.register(lineUp)).returnDomainEntity();
    }

    @Override
    public LineUp findById(Long id) {
        LineUpEntity entity = lineUpSpringJPARepository.findById(id)
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return entity.returnDomainEntity();
    }

    @Override
    public LineUp update(LineUp lineUp) {
        LineUpEntity lineUpEntity = lineUpSpringJPARepository.findById(lineUp.getId())
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return lineUpEntity.update(lineUp);
    }
}
