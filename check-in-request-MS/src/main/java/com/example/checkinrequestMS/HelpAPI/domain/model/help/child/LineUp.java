package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpJPAEntity;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineUp<T extends Progress> extends Help<T> {

    //check: Common Phrase랑 분단위가 아니라 분단위가 아닌 시간단위로 끝나는 시간을 설정하는것만 체크인과 다릅니다. -> DTO에서 처리 (?)
    //todo: 시간 설정 Help 엔티티의 책임으로 옮기기
    public static final String LINE_UP_TITLE = " 줄서기 요청";

    @Builder(access = AccessLevel.PROTECTED)
    protected LineUp(Long id, Long helpRegisterId, String title, Long placeId, Long reward, T progress, LocalDateTime start, LocalDateTime end) {
        super(id, helpRegisterId, title, start, end, placeId, reward, progress);
    }

    public static <T extends Progress> LineUp<T> of(LineUpRegisterDTO dto, Place place, T progress){
        return LineUp.<T>builder()
                .helpRegisterId(dto.getHelpRegisterId())
                .title(place.getPlaceName() + LINE_UP_TITLE)
                .start(dto.getStart())
                .end(dto.getEnd())
                .placeId(dto.getPlaceId())
                .progress(progress)
                .reward(dto.getReward())
                .build();
    }

    public static LineUp from(LineUpJPAEntity jpaEntity){
        return LineUp.builder()
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
