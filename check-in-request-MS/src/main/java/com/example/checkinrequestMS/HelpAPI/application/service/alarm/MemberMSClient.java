package com.example.checkinrequestMS.HelpAPI.application.service.alarm;

import java.util.List;

public interface MemberMSClient {
    List<String> getMemberTokenForAlarm(String PlaceName, Double x, Double y);

    void getMemberTokenForAlarm(Long helpRegisterId, String placeName, Double x, Double y);
}
