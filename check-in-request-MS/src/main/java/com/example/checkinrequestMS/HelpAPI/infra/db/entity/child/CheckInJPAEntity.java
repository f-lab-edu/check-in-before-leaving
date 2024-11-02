package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressVO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter.CHECK_IN_DISCRIMINATOR;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(CHECK_IN_DISCRIMINATOR)
public class CheckInJPAEntity extends HelpJPAEntity {

    @Builder(access = AccessLevel.PROTECTED)
    protected CheckInJPAEntity(Long id, Long helpRegisterId, String title, String placeId, ProgressVO progress, Long reward, LocalDateTime start, LocalDateTime end) {
        super(id, helpRegisterId, title, start, end, placeId, progress, reward);
    }

    public static CheckInJPAEntity from(Help help) {
        return CheckInJPAEntity.builder()
                .id(help.getId())
                .helpRegisterId(help.getHelpRegisterId())
                .title(help.getTitle())
                .placeId(help.getPlaceId())
                .progress(ProgressVO.from(help.getProgress()))
                .start(help.getStart())
                .end(help.getEnd())
                .reward(help.getReward())
                .build();
    }
}
