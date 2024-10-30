package com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity;


import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpJPAEntity;
import com.example.checkinrequestMS.fixtures.Variables;

public class LineUpJPAEntityFixture extends LineUpJPAEntity {

    public static LineUpJPAEntity createNoId(){
        return LineUpJPAEntity.builder()
                .helpRegisterId(1L)
                .title("title")
                .placeId(1L)
                .progressValue(ProgressValue.builder().completed(false).status(ProgressValue.CREATED).build())
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .reward(100L)
                .build();
    }
    public static LineUpJPAEntity createWithId(Long id){
        return LineUpJPAEntity.builder()
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
