package com.example.checkinrequestMS.HelpAPI.domain.dto.write.register;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class HelpRegisterDTO {

    protected Long helpRegisterId;
    protected Long placeId;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected Long reward;

    protected HelpRegisterDTO(Long helpRegisterId, Long placeId, LocalDateTime start, LocalDateTime end, Long reward) {
        this.helpRegisterId = helpRegisterId;
        this.placeId = placeId;
        this.start = start;
        this.end = end;
        this.reward = reward;
    }




}
