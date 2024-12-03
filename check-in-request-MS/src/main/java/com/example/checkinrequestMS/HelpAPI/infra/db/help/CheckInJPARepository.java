package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInRepository;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CheckInJPARepository implements CheckInRepository {

    private final CheckInSpringJPARepository checkInSpringJPARepository;

    @Override
    public Long save(CheckIn checkIn) {
        return checkInSpringJPARepository.save(CheckInEntity.toDB(checkIn)).getId();
    }

    @Override
    public CheckIn findById(Long id) {
        return CheckIn.toDomain(checkInSpringJPARepository.findById(id)
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO)));
    }
}
