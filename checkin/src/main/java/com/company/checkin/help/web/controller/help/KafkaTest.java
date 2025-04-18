package com.company.checkin.help.web.controller.help;

import com.company.checkin.help.application.alarm.AlarmService;
import com.company.checkin.place.domain.model.place.place.Place;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaTest {
    
    private final AlarmService alarmService;

    @PostMapping
    public void test() {
        Place place = Place.builder()
                .id(1L)
                .category(SearchCategory.FD6)
                .placeName("test")
                .roadAddressName("test")
                .phone("test")
                .x(1.0)
                .y(1.0)
                .build();

        alarmService.sendAlarmToUsersNearby(1L, place);
    }
}
