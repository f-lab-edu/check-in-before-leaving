package com.company.checkin.help.infra.adapter.db.repository.help.checkin;

import com.company.checkin.help.domain.exceptions.help.HelpErrorCode;
import com.company.checkin.help.domain.exceptions.help.HelpException;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckIn2;
import com.company.checkin.help.domain.model.help.checkin.CheckInRepository;
import com.company.checkin.help.domain.model.help.checkin.CheckInRepository2;
import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity;
import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CheckInJPARepository2 implements CheckInRepository2 {

    private final CheckInSpringJPARepository2 checkInSpringJPARepository;

    @Override
    public CheckIn2 save(CheckIn2 checkIn) {
        if (checkIn.getId() != null) throw new IllegalStateException();
        return checkInSpringJPARepository.save(CheckInEntity2.register(checkIn)).returnDomainModel();
    }

    @Override
    public CheckIn2 findById(Long id) {
        CheckInEntity2 checkInEntity = checkInSpringJPARepository.findById(id)
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return checkInEntity.returnDomainModel();
    }

    @Override
    public CheckIn2 update(CheckIn2 checkIn) {
        CheckInEntity2 checkInEntity = checkInSpringJPARepository.findById(checkIn.getId())
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return checkInEntity.update(checkIn);
    }
}
