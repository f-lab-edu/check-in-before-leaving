package com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.fixtures.Variables;

import java.time.LocalDateTime;

public class EtcFixture extends Etc{
    public static <T extends Progress> Etc<T> createEtcNoId(T progress){
        return Etc.<T>builder()
                .helpRegisterId(1L)
                .title("Etc title")
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .placeId(1L)
                .progress(progress)
                .reward(100L)
                .contents("Etc contents")
                .build();
    }
    public static <T extends Progress> Etc<T> createEtcWithId(T progress, Long id){
        return Etc.<T>builder()
                .id(id)
                .helpRegisterId(1L)
                .title("Etc title")
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .placeId(1L)
                .progress(progress)
                .reward(100L)
                .contents("Etc contents")
                .build();
    }
    public static Etc attachId(Etc etc, Long id){
        return Etc.builder()
                .id(id)
                .helpRegisterId(etc.getHelpRegisterId())
                .title(etc.getTitle())
                .start(etc.getStart())
                .end(etc.getEnd())
                .placeId(etc.getPlaceId())
                .progress(etc.getProgress())
                .reward(etc.getReward())
                .contents(etc.getContents())
                .build();
    }

}
