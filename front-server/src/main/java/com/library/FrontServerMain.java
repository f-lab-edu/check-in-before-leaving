package com.library;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrontServerMain {
    public static void main(String[] args) {
        //todo: Geolocation API 및 FCM은 프로덕션 환경에서 HTTPS만 지원. 이후 EC2에서는 HTTPS로 전환 필요.
        SpringApplication.run(FrontServerMain.class, args);
    }
}