package com.membercontext.memberAPI.web.pushMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.membercontext.memberAPI.application.exception.alarm.AlarmException;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.membercontext.memberAPI.application.exception.alarm.AlarmErrorCode.*;

@Component
@RequiredArgsConstructor
public class FireBaseCloudMessageClient {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/" + "push-test-4d79d/messages:send";

    public void sendMultipleMessages(List<String> tokens, String title, String message) {
        tokens.forEach(token -> {
            this.sendPushMessageTo(token, title, message);
        });
    }

    public void sendPushMessageTo(String targetToken, String title, String body) {
        try {
            initializeFirebaseApp();
            String message = makeMessage(targetToken, title, body);

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(message,
                    MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();

            client.newCall(request).execute();
        } catch (IOException e) {
            throw new AlarmException(OKHTTP_SENT_FAILED, e);
        }
    }

    private String makeMessage(String targetToken, String title, String body) {
        try {
            return FirebaseMessaging.getInstance().send(
                    Message.builder()
                            .setNotification(new Notification(title, body))
                            .setToken(targetToken)
                            .build());
        } catch (FirebaseMessagingException e) {
            throw new AlarmException(FCM_MESSAGE_CREATION_FAILED, e);
        }
    }
    
    private void initializeFirebaseApp() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("firebase.json").getInputStream()))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new AlarmException(FCM_RESOURCE_INPUT_STREAM_FAILED, e);
        }
    }

}
