package com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity;


import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInJPAEntity;
import com.example.checkinrequestMS.fixtures.Variables;

import java.time.LocalDateTime;

public class CheckInJPAEntityFixture extends CheckInJPAEntity {

    public static CheckInJPAEntity createNoId(){
        return CheckInJPAEntity.builder()
                .helpRegisterId(1L)
                .title("title")
                .placeId(1L)
                .progressValue(ProgressValue.builder().completed(false).status(ProgressValue.CREATED).build())
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .reward(100L)
                .build();
    }
    public static CheckInJPAEntity createWithId(Long id){
        return CheckInJPAEntity.builder()
                .id(id)
                .helpRegisterId(1L)
                .title("title")
                .placeId(1L)
                .progressValue(ProgressValue.builder().completed(false).status(ProgressValue.CREATED).build())
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .reward(100L)
                .build();
    }
}
