package com.example.checkinrequestMS.HelpAPI.web.controller.help.dto.response.help.select;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.read.HelpSelectController;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpSelectResponseTest {

    @Nested
    @DisplayName("CheckInSelectResponse")
    class CheckInSelectResponseTest {

        @Test
        @DisplayName("CheckInSelectResponse 생성 가능 여부")
        void canHandle() {
            //given
            CheckIn checkIn = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);
            HelpSelectController.CheckInSelectResponse sut = HelpSelectController.CheckInSelectResponse.createForTest();

            //when
            boolean result = sut.canHandle(checkIn);

            //then
            assertTrue(result);
        }

        @Test
        @DisplayName("CheckInSelectResponse 생성")
        void createResponse() {
            //given
            CheckIn checkIn = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);
            HelpSelectController.CheckInSelectResponse sut = HelpSelectController.CheckInSelectResponse.createForTest();

            //when
            HelpSelectController.HelpSelectResponse result = sut.createResponse(checkIn);

            //then
            assertEquals(result.getId(), checkIn.getId());
            assertEquals(result.getHelpRegisterId(), checkIn.getHelpRegisterId());
            assertEquals(result.getTitle(), checkIn.getTitle());
            assertEquals(result.getPlaceId(), checkIn.getPlaceId());
            assertEquals(result.getStart(), checkIn.getStart());
            assertEquals(result.getEnd(), checkIn.getEnd());
            assertEquals(result.getReward(), checkIn.getReward());
        }
    }


    @Nested
    @DisplayName("LineUpSelectResponse")
    class LineUpSelectResponse {
        @Test
        @DisplayName("LineUpSelectResponse 생성 가능 여부")
        void canHandle() {
            //given
            LineUp lineUp = LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L);
            HelpSelectController.LineUpSelectResponse sut = HelpSelectController.LineUpSelectResponse.createForTest();

            //when
            boolean result = sut.canHandle(lineUp);

            //then
            assertTrue(result);
        }

        @Test
        @DisplayName("LineUpSelectResponse 생성")
        void createResponse() {
            //given
            LineUp lineUp = LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L);
            HelpSelectController.LineUpSelectResponse sut = HelpSelectController.LineUpSelectResponse.createForTest();

            //when
            HelpSelectController.LineUpSelectResponse result = sut.createResponse(lineUp);

            //then
            assertEquals(result.getId(), lineUp.getId());
            assertEquals(result.getHelpRegisterId(), lineUp.getHelpRegisterId());
            assertEquals(result.getTitle(), lineUp.getTitle());
            assertEquals(result.getPlaceId(), lineUp.getPlaceId());
            assertEquals(result.getStart(), lineUp.getStart());
            assertEquals(result.getEnd(), lineUp.getEnd());
            assertEquals(result.getReward(), lineUp.getReward());
        }
    }

    @Nested
    @DisplayName("EtcSelectResponse")
    class EtcSelectResponse {
        @Test
        @DisplayName("EtcSelectResponse 생성 가능 여부")
        void canHandle() {
            //given
            Etc etc = EtcFixture.createEtcWithId(Progress.DEFAULT, 1L);
            HelpSelectController.EtcSelectResponse sut = HelpSelectController.EtcSelectResponse.createForTest();

            //when
            boolean result = sut.canHandle(etc);

            //then
            assertTrue(result);
        }

        @Test
        @DisplayName("EtcSelectResponse 생성")
        void createResponse() {
            //given
            Etc etc = EtcFixture.createEtcWithId(Progress.DEFAULT, 1L);
            HelpSelectController.EtcSelectResponse sut = HelpSelectController.EtcSelectResponse.createForTest();

            //when
            HelpSelectController.EtcSelectResponse result = sut.createResponse(etc);

            //then
            assertEquals(result.getId(), etc.getId());
            assertEquals(result.getHelpRegisterId(), etc.getHelpRegisterId());
            assertEquals(result.getTitle(), etc.getTitle());
            assertEquals(result.getPlaceId(), etc.getPlaceId());
            assertEquals(result.getStart(), etc.getStart());
            assertEquals(result.getEnd(), etc.getEnd());
            assertEquals(result.getReward(), etc.getReward());
            assertEquals(result.getContents(), etc.getContents());
        }
    }
}