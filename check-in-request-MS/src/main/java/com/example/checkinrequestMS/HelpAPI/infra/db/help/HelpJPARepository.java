package com.example.checkinrequestMS.HelpAPI.infra.db.help;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPARead;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPASave;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpJPAEntity;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.HelpSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child.CheckInSelectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class HelpJPARepository implements HelpDBAdapter {
    
    private final HelpSpringJPARepository helpSpringJPARepository;

    @Override
    public Long save(Help help) {
        return helpSpringJPARepository.save(mapToJPAEntity(help)).getId();
    }

    @Override
    public Help findById(Long id) {
        //Help를 내려주지 말고, DTO를 바로 내려 준다면.
        HelpJPAEntity helpJPAEntity = helpSpringJPARepository.findById(id).get();

        return mapToDomain(helpJPAEntity);
    }

    @Override
    public List<Help> findAllHelpByPlaceId(List<String> ids) {
        List<HelpJPAEntity> list = helpSpringJPARepository.findAllByPlaceId(ids);
        return list.stream().map(this::mapToDomain).toList();
    }

    private Help mapToDomain(HelpJPAEntity helpJPAEntity) {
        if (helpJPAEntity instanceof CheckInJPAEntity) {
            return CheckIn.from((CheckInJPAEntity) helpJPAEntity);
        } else if (helpJPAEntity instanceof EtcJPAEntity) {
            return Etc.from((EtcJPAEntity) helpJPAEntity);
        } else if (helpJPAEntity instanceof LineUpJPAEntity) {
            return LineUp.from((LineUpJPAEntity) helpJPAEntity);
        }
        throw new RuntimeException("Help not found");
    }

    private <T extends Help> HelpJPAEntity mapToJPAEntity(T help) {

        if (help instanceof CheckIn) {
            return CheckInJPAEntity.from((CheckIn) help);
        } else if (help instanceof Etc) {
            return EtcJPAEntity.from((Etc) help);
        } else if (help instanceof LineUp) {
            return LineUpJPAEntity.from((LineUp) help);
        }
        throw new RuntimeException("HelpJPAEntity not found");
    }

}
