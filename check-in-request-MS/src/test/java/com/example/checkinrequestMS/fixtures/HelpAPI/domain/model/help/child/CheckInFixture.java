package com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.fixtures.Variables;

import java.time.LocalDateTime;


public class CheckInFixture extends CheckIn{

   public static <T extends Progress> CheckIn<T> createCheckInNoId(T progress){
        return CheckIn.<T>builder()
                .helpRegisterId(1L)
                .title("CheckIn title")
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .placeId(1L)
                .progress(progress)
                .reward(100L)
                .build();
   }
    public static <T extends Progress> CheckIn<T> createCheckInWithId(T progress, Long id){
        return CheckIn.<T>builder()
                .id(1L)
                .helpRegisterId(1L)
                .title("CheckIn title")
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .placeId(1L)
                .progress(progress)
                .reward(100L)
                .build();
    }
   public static CheckIn attachId(CheckIn checkIn, Long id){
        return CheckIn.builder()
                .id(id)
                .helpRegisterId(checkIn.getHelpRegisterId())
                .title(checkIn.getTitle())
                .start(checkIn.getStart())
                .end(checkIn.getEnd())
                .placeId(checkIn.getPlaceId())
                .progress(checkIn.getProgress())
                .reward(checkIn.getReward())
                .build();
    }




}
