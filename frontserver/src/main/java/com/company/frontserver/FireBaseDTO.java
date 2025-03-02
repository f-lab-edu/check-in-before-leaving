package com.company.frontserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FireBaseDTO {

    @Value("${FCM_API_KEY}")
    private String apiKey;

    @Value("${FCM_AUTH_DOMAIN}")
    private String authDomain;

    @Value("${FCM_PROJECT_ID}")
    private String projectId;

    @Value("${FCM_STORAGE_BUCKET}")
    private String storageBucket;

    @Value("${FCM_MESSAGING_SENDER_ID}")
    private String messagingSenderId;

    @Value("${FCM_APP_ID}")
    private String appId;

    public String getApiKey() {
        return apiKey;
    }

    public String getAuthDomain() {
        return authDomain;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getStorageBucket() {
        return storageBucket;
    }

    public String getMessagingSenderId() {
        return messagingSenderId;
    }

    public String getAppId() {
        return appId;
    }
}
