package com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.fixtures.Variables;

import java.time.LocalDateTime;

public class LineUpFixture extends LineUp {

    public static <T extends Progress> LineUp<T> createLineUpNoId(T progress) {
        return LineUp.<T>builder()
                .helpRegisterId(1L)
                .title("LineUp title")
                .start(Variables.startTime)
                .end(Variables.startTime.plusHours(1L))
                .placeId(1L)
                .progress(progress)
                .reward(100L)
                .build();
    }
    public static <T extends Progress> LineUp<T> createLineUpWithId(T progress, Long id){
        return LineUp.<T>builder()
                .id(1L)
                .helpRegisterId(1L)
                .title("LineUp title")
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .placeId(1L)
                .progress(progress)
                .reward(100L)
                .build();
    }
    public static LineUp attachId(LineUp lineUp, Long id){
        return LineUp.builder()
                .id(id)
                .helpRegisterId(lineUp.getHelpRegisterId())
                .title(lineUp.getTitle())
                .start(lineUp.getStart())
                .end(lineUp.getEnd())
                .placeId(lineUp.getPlaceId())
                .progress(lineUp.getProgress())
                .reward(lineUp.getReward())
                .build();
    }

}
