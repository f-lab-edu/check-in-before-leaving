package com.example.checkinrequestMS.HelpAPI.web.dto.help.etc;


import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.dto.progress.ProgressDTO;
import com.example.checkinrequestMS.PlaceAPI.web.dto.PlaceDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class EtcDTO {
    private Long id;
    private Long memberId;
    private String title;
    private PlaceDTO place;
    private LocalDateTime start;
    private LocalDateTime end;
    private String contents;
    private ProgressDTO progress;
    private Long reward;

    public static EtcDTO from(Etc etc) {

        return EtcDTO.builder()
                .id(etc.getId())
                .memberId(etc.getMemberId())
                .title(etc.getTitle())
                //.place(PlaceDTO.from(etc.getPlace()))
                .start(etc.getStart())
                .end(etc.getEnd())
                .contents(etc.getContents())
                .progress(ProgressDTO.from(etc.getProgress()))
                .reward(etc.getReward())
                .build();

    }
}
