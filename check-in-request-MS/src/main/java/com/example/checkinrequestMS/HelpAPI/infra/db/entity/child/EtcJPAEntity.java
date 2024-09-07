package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

import static com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter.ETC_DISCRIMINATOR;


@Getter
@Entity
@DiscriminatorValue(ETC_DISCRIMINATOR)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EtcJPAEntity extends HelpJPAEntity {

    private String contents;

    @Builder(access = AccessLevel.PROTECTED)
    protected EtcJPAEntity(Long id, Long helpRegisterId, String title, Long placeId, ProgressValue progressValue, Long reward, LocalDateTime start, LocalDateTime end, String contents) {
        super(id, helpRegisterId, title, start, end, placeId, progressValue, reward);
        this.contents = contents;
    }


    public static EtcJPAEntity from(Etc etc){
        return EtcJPAEntity.builder()
                .title(etc.getTitle())
                .placeId(etc.getPlaceId())
                .start(etc.getStart())
                .end(etc.getEnd())
                .contents(etc.getContents())
                .reward(etc.getReward())
                .build();
    }
}
