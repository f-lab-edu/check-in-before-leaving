package com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.select;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.read.HelpSelectController;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HelpSelectResponseTest {

    @Nested
    @DisplayName("HelpSelectResponse")
    class HelpSelectResponse {

        static Stream<Arguments> models() {
            return Stream.of(
                    Arguments.of(CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L), HelpSelectController.CheckInSelectResponse.class.getSimpleName()),
                    Arguments.of(LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L), HelpSelectController.LineUpSelectResponse.class.getSimpleName()),
                    Arguments.of(EtcFixture.createEtcWithId(Progress.DEFAULT, 1L), HelpSelectController.EtcSelectResponse.class.getSimpleName())
            );
        }

        @ParameterizedTest(name = "{index}: {1}")
        @MethodSource("models")
        void from(Help help, String className) {

            //when
            HelpSelectController.HelpSelectResponse sut = HelpSelectController.HelpSelectResponse.from(help);

            //then
            assertEquals(sut.getClass().getSimpleName(), className);
        }
    }

    @Nested
    @DisplayName("CheckInSelectResponse")
    class CheckInSelectResponse {
        @Test
        void from() {
            //given
            CheckIn checkIn = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);
            //when
            HelpSelectController.CheckInSelectResponse sut = HelpSelectController.CheckInSelectResponse.from(checkIn);

            //then
            assertEquals(sut.getId(), checkIn.getId());
            assertEquals(sut.getHelpRegisterId(), checkIn.getHelpRegisterId());
            assertEquals(sut.getTitle(), checkIn.getTitle());
            assertEquals(sut.getPlaceId(), checkIn.getPlaceId());
            assertEquals(sut.getStart(), checkIn.getStart());
            assertEquals(sut.getEnd(), checkIn.getEnd());
            assertEquals(sut.getReward(), checkIn.getReward());
        }
    }

    @Nested
    @DisplayName("LineUpSelectResponse")
    class LineUpSelectResponse {
        @Test
        void from() {
            //given
            CheckIn checkIn = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);
            //when
            HelpSelectController.CheckInSelectResponse sut = HelpSelectController.CheckInSelectResponse.from(checkIn);

            //then
            assertEquals(sut.getId(), checkIn.getId());
            assertEquals(sut.getHelpRegisterId(), checkIn.getHelpRegisterId());
            assertEquals(sut.getTitle(), checkIn.getTitle());
            assertEquals(sut.getPlaceId(), checkIn.getPlaceId());
            assertEquals(sut.getStart(), checkIn.getStart());
            assertEquals(sut.getEnd(), checkIn.getEnd());
            assertEquals(sut.getReward(), checkIn.getReward());
        }
    }
    
    @Nested
    @DisplayName("EtcSelectResponse")
    class EtcSelectResponse {
        @Test
        void from() {
            //given
            CheckIn checkIn = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);
            //when
            HelpSelectController.CheckInSelectResponse sut = HelpSelectController.CheckInSelectResponse.from(checkIn);

            //then
            assertEquals(sut.getId(), checkIn.getId());
            assertEquals(sut.getHelpRegisterId(), checkIn.getHelpRegisterId());
            assertEquals(sut.getTitle(), checkIn.getTitle());
            assertEquals(sut.getPlaceId(), checkIn.getPlaceId());
            assertEquals(sut.getStart(), checkIn.getStart());
            assertEquals(sut.getEnd(), checkIn.getEnd());
            assertEquals(sut.getReward(), checkIn.getReward());
        }
    }
}