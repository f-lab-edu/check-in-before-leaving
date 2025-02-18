package com.example.checkinrequestMS.HelpAPI.domain.jpa_version;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class CheckInJPATest {

    @Autowired
    private CheckInJPASpringJPARepository db;

    @Test
    void save() {
        CheckInServiceJPA.Creation dto = CheckInServiceJPA.Creation
                .builder()
                .option(10)
                .start(LocalDateTime.now())
                .helpRegisterId(1L)
                .placeName("name")
                .reward(100L)
                .placeId("hellow")
                .build();
        CheckInJPA jpa = CheckInJPA.register(dto);
        db.save(jpa);


    }

}