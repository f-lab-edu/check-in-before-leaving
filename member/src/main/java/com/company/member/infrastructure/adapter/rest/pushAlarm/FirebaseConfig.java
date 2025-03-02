package com.company.member.infrastructure.adapter.rest.pushAlarm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.company.member.infrastructure.exceptions.pushAlarm.PushAlarmException;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static com.company.member.infrastructure.exceptions.pushAlarm.PushAlarmErrorCode.FCM_RESOURCE_INPUT_STREAM_FAILED;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    private void initializeFirebaseApp() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("firebase.json").getInputStream()))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new PushAlarmException(FCM_RESOURCE_INPUT_STREAM_FAILED, e);
        }
    }
}
