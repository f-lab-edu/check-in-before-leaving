package com.example.checkinrequestMS.HelpAPI.application.service.alarm;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final MemberMSClient memberMSClient;

    public void sendAlarmToUsersNearby(String placeName, Double x, Double y) {
        // OkHttp
        memberMSClient.getMemberTokenForAlarm(placeName, x, y);
    }

    public void sendAlarmToUsersNearby(Long helpRegisterId, Place place) {
        // Kafka
        memberMSClient.getMemberTokenForAlarm(helpRegisterId, place.getPlaceName(), place.getX(), place.getY());
    }
}
