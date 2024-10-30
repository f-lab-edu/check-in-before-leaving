package com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity;


import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcJPAEntity;
import com.example.checkinrequestMS.fixtures.Variables;

public class EtcJPAEntityFixture extends EtcJPAEntity {

    public static EtcJPAEntity createNoId(){
        return EtcJPAEntity.builder()
                .helpRegisterId(1L)
                .title("title")
                .placeId(1L)
                .progressValue(ProgressValue.builder().completed(false).status(ProgressValue.CREATED).build())
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .reward(100L)
                .contents("contents")
                .build();
    }
    public static EtcJPAEntity createWithId(Long id){
        return EtcJPAEntity.builder()
                .id(id)
                .helpRegisterId(1L)
                .title("title")
                .placeId(1L)
                .progressValue(ProgressValue.builder().completed(false).status(ProgressValue.CREATED).build())
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .reward(100L)
                .contents("contents")
                .build();
    }
}
