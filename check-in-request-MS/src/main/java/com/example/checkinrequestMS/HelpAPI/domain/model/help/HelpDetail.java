package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpDetailEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
public final class HelpDetail {

    private final Long helpRegisterId;
    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String placeId;
    private final Long reward;

    @Builder(access = AccessLevel.PRIVATE)
    private HelpDetail(@NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward) {
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.placeId = placeId;
        this.reward = reward;
    }

    public static HelpDetail registerCheckIn(CheckInService.Registration dto) {
        return HelpDetail.builder()
                .helpRegisterId(dto.getHelpRegisterId())
                .title(dto.getTitle())
                .start(dto.getStart())
                .end(dto.getStart().plusMinutes(dto.getOption()))
                .placeId(dto.getPlaceId())
                .reward(dto.getReward())
                .build();
    }

    public static HelpDetail toDomain(HelpDetailEntity entity) {
        return HelpDetail.builder()
                .helpRegisterId(entity.getHelpRegisterId())
                .title(entity.getTitle())
                .start(entity.getStart())
                .end(entity.getEnd())
                .placeId(entity.getPlaceId())
                .reward(entity.getReward())
                .build();
    }

    //for Test
    public static HelpDetail createForTest() {
        return HelpDetail.builder()
                .helpRegisterId(1L)
                .title("title")
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .placeId("placeId")
                .reward(100L)
                .build();
    }


}
