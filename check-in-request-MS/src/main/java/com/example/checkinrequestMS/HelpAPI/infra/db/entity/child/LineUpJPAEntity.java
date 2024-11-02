package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressVO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter.LINE_UP_DISCRIMINATOR;


@Getter
@Entity
@DiscriminatorValue(LINE_UP_DISCRIMINATOR)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineUpJPAEntity extends HelpJPAEntity {

    @Builder(access = AccessLevel.PROTECTED)
    protected LineUpJPAEntity(Long id, Long helpRegisterId, String title, String placeId, ProgressVO progress, Long reward, LocalDateTime start, LocalDateTime end) {
        super(id, helpRegisterId, title, start, end, placeId, progress, reward);
    }

    public static LineUpJPAEntity from(LineUp lineUp) {
        return LineUpJPAEntity.builder()
                .id(lineUp.getId())
                .helpRegisterId(lineUp.getHelpRegisterId())
                .progress(ProgressVO.from(lineUp.getProgress()))
                .placeId(lineUp.getPlaceId())
                .title(lineUp.getTitle())
                .start(lineUp.getStart())
                .end(lineUp.getEnd())
                .reward(lineUp.getReward())
                .build();
    }

}
