package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcRepository;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EtcJPARepository implements EtcRepository {

    private final EtcSpringJPARepository etcSpringJPARepository;

    @Override
    public Etc save(Etc etc) {
        return etcSpringJPARepository.save(EtcEntity.register(etc)).returnDomainEntity();
    }

    @Override
    public Etc findById(Long id) {
        EtcEntity entity = etcSpringJPARepository.findById(id)
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return entity.returnDomainEntity();
    }

    @Override
    public Etc update(Etc etc) {
        EtcEntity etcEntity = etcSpringJPARepository.findById(etc.getId())
                .orElseThrow(() -> new HelpException(HelpErrorCode.NO_HELP_INFO));
        return etcEntity.update(etc);
    }
}
