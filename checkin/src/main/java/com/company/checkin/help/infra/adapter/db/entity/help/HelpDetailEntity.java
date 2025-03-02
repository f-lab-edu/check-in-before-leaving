package com.company.checkin.help.infra.adapter.db.entity.help;

import com.company.checkin.help.domain.model.help.HelpDetail;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class HelpDetailEntity {

    @Column(nullable = false)
    private final Long helpRegisterId; //help owner

    @Column(nullable = false)
    private final String title;

    @Column(nullable = false)
    private final LocalDateTime start;

    @Column(nullable = false)
    private final LocalDateTime end;

    @Column(nullable = false)
    private final String placeId;

    @Column(nullable = false)
    private final Long reward;

    @Builder(access = AccessLevel.PRIVATE)
    private HelpDetailEntity(@NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward) {
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.placeId = placeId;
        this.reward = reward;
    }

    public static HelpDetailEntity from(@NonNull HelpDetail.DTO dto) {
        return HelpDetailEntity.builder()
                .helpRegisterId(dto.getHelpRegisterId())
                .title(dto.getTitle())
                .start(dto.getStart())
                .end(dto.getEnd())
                .placeId(dto.getPlaceId())
                .reward(dto.getReward())
                .build();
    }
    // 1. 인터페이스로 해결하면 DTO가 인프라에 의존하게 된다
    // 2. 내부 클래스로 해결
    // 3. 각 메서드 만들기.

}
