package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class HelpDetailEntity {

    private final Long helpRegisterId; //help owner
    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String placeId;
    private final Long reward;

    @Builder(access = AccessLevel.PRIVATE)
    private HelpDetailEntity(Long helpRegisterId, String title, LocalDateTime start, LocalDateTime end, String placeId, Long reward) {
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.placeId = placeId;
        this.reward = reward;
    }

    public static HelpDetailEntity toDB(HelpDetail helpDetail) {
        return HelpDetailEntity.builder()
                .helpRegisterId(helpDetail.getHelpRegisterId())
                .title(helpDetail.getTitle())
                .start(helpDetail.getStart())
                .end(helpDetail.getEnd())
                .placeId(helpDetail.getPlaceId())
                .reward(helpDetail.getReward())
                .build();
    }

}
