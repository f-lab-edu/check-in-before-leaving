package com.company.checkin.help.application.alarm;

import com.company.checkin.place.domain.model.place.place.Place;
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
