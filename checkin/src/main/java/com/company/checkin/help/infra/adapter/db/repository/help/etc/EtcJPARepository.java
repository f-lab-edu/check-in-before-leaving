package com.company.checkin.help.infra.adapter.db.repository.help.etc;

import com.company.checkin.help.domain.exceptions.help.HelpErrorCode;
import com.company.checkin.help.domain.exceptions.help.HelpException;
import com.company.checkin.help.domain.model.help.etc.Etc;
import com.company.checkin.help.domain.model.help.etc.EtcRepository;
import com.company.checkin.help.infra.adapter.db.entity.help.etc.EtcEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EtcJPARepository implements EtcRepository {

    private final EtcSpringJPARepository etcSpringJPARepository;

    @Override
    public Etc save(Etc etc) {
        return etcSpringJPARepository.save(EtcEntity.register(etc)).returnDomainModel();
    }

    @Override
    public Etc findById(Long id) {
        EtcEntity entity = etcSpringJPARepository.findById(id)
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return entity.returnDomainModel();
    }

    @Override
    public Etc update(Etc etc) {
        EtcEntity etcEntity = etcSpringJPARepository.findById(etc.getId())
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return etcEntity.update(etc);
    }
}
