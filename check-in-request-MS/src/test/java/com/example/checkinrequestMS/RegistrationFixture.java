//package com.example.checkinrequestMS;
//
//
//import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;
//import lombok.Builder;
//
//import java.time.LocalDateTime;
//
//@Builder
//public class RegistrationFixture {
//
//    private Long helpRegisterId;
//
//    private String title;
//
//    private String placeName;
//
//    private String contents;
//
//    private LocalDateTime start;
//
//    private int option;
//
//    private Long reward;
//
//    private String placeId;
//
//    public EtcService.Registration customize_EtcService_Registration() {
//        return EtcService.Registration.createForTest(
//                helpRegisterId,
//                placeId,
//                placeName,
//                start,
//                title,
//                contents,
//                option,
//                reward
//        );
//    }
//
//    public static EtcService.Registration createFixture_EtcService_Registration() {
//        RegistrationFixture fixture = RegistrationFixture.builder()
//                .helpRegisterId(1L)
//                .title("title")
//                .contents("contents")
//                .start(LocalDateTime.now())
//                .option(1)
//                .reward(1L)
//                .placeId("placeId")
//                .build();
//        return fixture.customize_EtcService_Registration();
//    }
//
//
//}
