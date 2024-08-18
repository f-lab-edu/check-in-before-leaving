package com.example.checkinrequestMS.HelpAPI.domain.service.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;


@SpringBootTest
@Disabled
// DB 입력 확인을 위한 통합 테스트 코드 입니다.
class CheckInCRUDServiceTest_Integrated {

    @Autowired
    CheckInCRUDService sut;

    @Autowired
    PlaceJPARepository placeRepository;

    @Test
    void registerCheckIn() {
        //given
        Place place = mock(Place.class);
        placeRepository.save(place);
        //Place Name API에서 바로 저장해서 지금은 null
        //Id 는 auto increment로 되어있어서 바뀜.

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyMinLater = now.plusMinutes(30);
        CheckIn checkInToRegister = CheckIn.builder().memberId(1L).title("title").placeId(1L).start(now).end(thirtyMinLater).reward(100L).build();

        //when
        sut.registerCheckIn(checkInToRegister);
    }
}