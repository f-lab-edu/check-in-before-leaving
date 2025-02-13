package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInRepository;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CheckInJPARepository implements CheckInRepository {

    private final CheckInSpringJPARepository checkInSpringJPARepository;

    @Override
    public CheckIn save(CheckIn checkIn) {
        if (checkIn.getId() != null) throw new IllegalStateException();
        return checkInSpringJPARepository.save(CheckInEntity.register(checkIn)).returnDomainModel();
    }

    @Override
    public CheckIn findById(Long id) {
        CheckInEntity checkInEntity = checkInSpringJPARepository.findById(id)
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return checkInEntity.returnDomainModel();
    }

    @Override
    public CheckIn update(CheckIn checkIn) {
        CheckInEntity checkInEntity = checkInSpringJPARepository.findById(checkIn.getId())
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return checkInEntity.update(checkIn);
    }
}
