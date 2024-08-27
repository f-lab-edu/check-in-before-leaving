package com.example.checkinrequestMS.HelpAPI.web.dto.help.checkIn;


import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.dto.progress.ProgressDTO;
import com.example.checkinrequestMS.PlaceAPI.web.dto.PlaceDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CheckInDTO {
    private Long id;
    private Long memberId;
    private String title;
    private PlaceDTO place;
    private LocalDateTime start;
    private LocalDateTime end;
    private ProgressDTO progress;
    private Long reward;

    public static CheckInDTO from(CheckIn checkIn) {

        return CheckInDTO.builder()
                .id(checkIn.getId())
                .memberId(checkIn.getMemberId())
                .title(checkIn.getTitle())
                .place(PlaceDTO.from(checkIn.getPlace()))
                .start(checkIn.getStart())
                .end(checkIn.getEnd())
                .progress(ProgressDTO.from(checkIn.getProgress()))
                .reward(checkIn.getReward())
                .build();

    }
}
