package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Authenticated;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatedSelectResponseTest {

    @Test
    void from() {
        //given
        Authenticated authenticated = Authenticated.of(1L, "photoPath", false);

        //when
        AuthenticatedSelectResponse sut = AuthenticatedSelectResponse.from(authenticated);

        //then
        assertEquals(sut.getHelperId(), authenticated.getHelperId());
        assertEquals(sut.getPhotoPath(), authenticated.getPhotoPath());
        assertEquals(sut.isCompleted(), authenticated.isCompleted());
    }
}