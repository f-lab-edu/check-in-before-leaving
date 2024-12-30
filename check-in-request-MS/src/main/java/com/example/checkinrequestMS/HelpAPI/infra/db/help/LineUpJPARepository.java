package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
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
    public Long save(LineUp lineUp) {
        return lineUpSpringJPARepository.save(LineUpEntity.register(lineUp)).getId();
    }

    @Override
    public LineUp findById(Long id) {
        return LineUp.toDomain(lineUpSpringJPARepository.findById(id)
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO)));
    }
}
