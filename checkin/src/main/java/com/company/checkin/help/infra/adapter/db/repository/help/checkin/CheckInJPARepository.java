package com.company.checkin.help.infra.adapter.db.repository.help.checkin;

import com.company.checkin.help.domain.exceptions.help.HelpErrorCode;
import com.company.checkin.help.domain.exceptions.help.HelpException;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInRepository;
import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity;
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
