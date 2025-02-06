package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public final class HelpDetail {

    private final Long helpRegisterId;
    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String placeId;
    private final Long reward;

    @Builder
    private HelpDetail(@NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward) {
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.placeId = placeId;
        this.reward = reward;
    }

    public static HelpDetail from(@NonNull DTO dto) {
        return HelpDetail.builder()
                .helpRegisterId(dto.getHelpRegisterId())
                .title(dto.getTitle())
                .start(dto.getStart())
                .end(dto.getEnd())
                .placeId(dto.getPlaceId())
                .reward(dto.getReward())
                .build();
    }

    public interface DTO {
        Long getHelpRegisterId();

        String getTitle();

        LocalDateTime getStart();

        LocalDateTime getEnd();

        String getPlaceId();

        Long getReward();
    }
}
