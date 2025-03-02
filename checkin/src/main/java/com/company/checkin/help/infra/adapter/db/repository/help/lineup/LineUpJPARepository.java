package com.company.checkin.help.infra.adapter.db.repository.help.lineup;

import com.company.checkin.help.domain.exceptions.help.HelpErrorCode;
import com.company.checkin.help.domain.exceptions.help.HelpException;
import com.company.checkin.help.domain.model.help.lineup.LineUp;
import com.company.checkin.help.domain.model.help.lineup.LineUpRepository;
import com.company.checkin.help.infra.adapter.db.entity.help.lineup.LineUpEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LineUpJPARepository implements LineUpRepository {

    private final LineUpSpringJPARepository lineUpSpringJPARepository;

    @Override
    public LineUp save(LineUp lineUp) {
        return lineUpSpringJPARepository.save(LineUpEntity.register(lineUp)).returnDomainModel();
    }

    @Override
    public LineUp findById(Long id) {
        LineUpEntity entity = lineUpSpringJPARepository.findById(id)
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return entity.returnDomainModel();
    }

    @Override
    public LineUp update(LineUp lineUp) {
        LineUpEntity lineUpEntity = lineUpSpringJPARepository.findById(lineUp.getId())
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return lineUpEntity.update(lineUp);
    }
}
