package com.company.checkin.help.application.alarm;

import java.util.List;

public interface MemberMSClient {
    List<String> getMemberTokenForAlarm(String PlaceName, Double x, Double y);

    void getMemberTokenForAlarm(Long helpRegisterId, String placeName, Double x, Double y);
}
