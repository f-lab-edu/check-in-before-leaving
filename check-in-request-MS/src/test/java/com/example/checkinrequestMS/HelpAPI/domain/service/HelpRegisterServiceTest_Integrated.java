package com.example.checkinrequestMS.HelpAPI.domain.service;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Disabled
// DB 입력 확인을 위한 통합 테스트 코드 입니다.
class HelpRegisterServiceTest_Integrated {

    @Autowired
    HelpRegisterService sut;

    @Autowired
    PlaceRepository placeRepository;

    @Test
    void registerCheckIn() {
        //given
        Place place = Place.createEmptyPlaceWithOnlyId(1L);
        placeRepository.save(place);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyMinLater = now.plusMinutes(30);
        CheckIn checkInToRegister = CheckIn.builder().memberId(1L).title("title").place(place).start(now).end(thirtyMinLater).reward(100L).build();

        //when
        sut.registerCheckIn(checkInToRegister);

    }
}