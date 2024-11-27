package com.example.checkinrequestMS.HelpAPI.web.APIController;

import com.example.checkinrequestMS.HelpAPI.infra.MSClient.MSClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Disabled
class MemberAPIControllerTest {

    //
    @Autowired
    private MSClient sut;

    @Test
    void getMembersToAlarm() throws IOException {
        double x = 0;
        double y = 0;
        sut.getMemberTokenForAlarm("테스트 가게", x, y);
    }
}