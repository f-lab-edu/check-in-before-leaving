package com.example.checkinrequestMS.HelpAPI.infra.MSClient;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.MemberMSClient;
import com.example.checkinrequestMS.HelpAPI.domain.model.alarm.HelpAlarmEvent;
import com.example.checkinrequestMS.HelpAPI.infra.MSClient.kafka.AlarmEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MSClient implements MemberMSClient {

    private static final String API_MEMBER_URL = "http://localhost:8080";
    private static final String sendMessageUri = "/sendMessage";

    //   private final AlarmEventProducer alarmEventProducer;
    private final ObjectMapper objectMapper;

    public static final String HELP_ALARM_TITLE = "도움 요청";
    public static final String HELP_ALARM_MESSAGE_STRUCTURE = "관련 도움 요청이 등록 되었습니다.";

    @Override
    public List<String> getMemberTokenForAlarm(String placeName, Double x, Double y) {

        RequestBody body = null;
        String message = placeName + HELP_ALARM_MESSAGE_STRUCTURE;
        try {
            body = RequestBody.create(objectMapper.writeValueAsString(new AlarmForm(x, y, HELP_ALARM_TITLE, message)),
                    MediaType.get("application/json; charset=utf-8"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .post(body)
                .url(API_MEMBER_URL + sendMessageUri)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AlarmForm {
        double x;
        double y;
        String title;
        String message;
    }


    @Override
    public void getMemberTokenForAlarm(Long helpRegisterId, String placeName, Double x, Double y) {
        AlarmForm alarmForm = new AlarmForm(x, y, HELP_ALARM_TITLE, placeName + HELP_ALARM_MESSAGE_STRUCTURE);
        HelpAlarmEvent alarmEvent = new HelpAlarmEvent(helpRegisterId, alarmForm);

        //alarmEventProducer.sendAlarmEvent(alarmEvent);
    }


}
