package com.example.checkinrequestMS.HelpAPI.domain.jpa_version;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(force = true)
public class HelpDetailJPA {

    private final Long helpRegisterId;
    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String placeId;
    private final Long reward;

    @Builder(access = AccessLevel.PRIVATE)
    private HelpDetailJPA(@NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward) {
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.placeId = placeId;
        this.reward = reward;
    }

    public static HelpDetailJPA from(@NonNull DTO dto) {
        return HelpDetailJPA.builder()
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
