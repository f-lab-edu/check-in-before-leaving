package com.example.checkinrequestMS.HelpAPI.web.dto.help.lineUp;


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
public class LineUpDTO {
    private Long id;
    private Long memberId;
    private String title;
    private PlaceDTO place;
    private LocalDateTime start;
    private LocalDateTime end;
    private ProgressDTO progress;
    private Long reward;

    public static LineUpDTO from(LineUp lineUp) {

        return LineUpDTO.builder()
                .id(lineUp.getId())
                .memberId(lineUp.getMemberId())
                .title(lineUp.getTitle())
                //.place(PlaceDTO.from(lineUp.getPlace()))
                .start(lineUp.getStart())
                .end(lineUp.getEnd())
                .progress(ProgressDTO.from(lineUp.getProgress()))
                .reward(lineUp.getReward())
                .build();

    }
}
