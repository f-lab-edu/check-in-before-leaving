package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInJPAEntity;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckIn<T extends Progress> extends Help<T> {

    public static final String CHECK_IN_TITLE = "체크인 요청";

    @Builder(access = AccessLevel.PROTECTED)
    protected CheckIn(Long id, Long helpRegisterId, String title, String placeId, Long reward, T progress, LocalDateTime start, LocalDateTime end) {
        super(id, helpRegisterId, title, start, end, placeId, reward, progress);
    }

    public static <T extends Progress> CheckIn<T> of(CheckInRegisterDTO dto, Place place, T progress) {
        return CheckIn.<T>builder()
                .helpRegisterId(dto.getHelpRegisterId())
                .title(place.getPlaceName() + CHECK_IN_TITLE)
                .start(dto.getStart())
                .end(dto.getEnd())
                .placeId(dto.getPlaceId())
                .progress(progress)
                .reward(dto.getReward())
                .build();
    }

    public static CheckIn from(CheckInJPAEntity jpaEntity) {
        return CheckIn.builder()
                .id(jpaEntity.getId())
                .helpRegisterId(jpaEntity.getHelpRegisterId())
                .title(jpaEntity.getTitle())
                .start(jpaEntity.getStart())
                .end(jpaEntity.getEnd())
                .placeId(jpaEntity.getPlaceId())
                .progress(getProgress(jpaEntity.getProgressValue()))
                .reward(jpaEntity.getReward())
                .build();
    }


}
