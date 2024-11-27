package com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.select;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.select.child.CheckInSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.select.child.EtcSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.select.child.LineUpSelectResponse;

import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HelpSelectResponseTest {

    static Stream<Arguments> models() {
        return Stream.of(
                Arguments.of(CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L), CheckInSelectResponse.class.getSimpleName()),
                Arguments.of(LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L), LineUpSelectResponse.class.getSimpleName()),
                Arguments.of(EtcFixture.createEtcWithId(Progress.DEFAULT, 1L), EtcSelectResponse.class.getSimpleName())
        );
    }

    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("models")
    void from(Help help, String className) {

        //when
        HelpSelectResponse sut = HelpSelectResponse.from(help);

        //then
        assertEquals(sut.getClass().getSimpleName(), className);
    }
}