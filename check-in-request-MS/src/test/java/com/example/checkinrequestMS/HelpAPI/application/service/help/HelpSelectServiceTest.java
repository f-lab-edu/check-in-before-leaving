package com.example.checkinrequestMS.HelpAPI.application.service.help;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.stub.HelpDBAdapterStub;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HelpSelectServiceTest {

    @InjectMocks
    private HelpSelectService sut;

    @Spy
    private HelpDBAdapterStub helpDBAdapter;

    @Nested
    class selectHelp {
        static Stream<Arguments> models() {
            return Stream.of(
                    Arguments.of(CheckInFixture.createCheckInWithId(Created.create(), 1L), "체크인"),
                    Arguments.of(LineUpFixture.createLineUpWithId(Created.create(), 2L), "줄서기")
            );
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("models")
        @DisplayName("체크인, 줄서기 조회 성공")
        void selectALLHelps(Help help, String name) {
            //given
            Long savedId = helpDBAdapter.save(help);

            //when
            Help result = sut.selectHelp(1L);

            //when
            assertEquals(savedId, result.getId());
            assertEquals(help.getHelpRegisterId(), result.getHelpRegisterId());
            assertEquals(help.getTitle(), result.getTitle());
            assertEquals(help.getStart(), result.getStart());
            assertEquals(help.getEnd(), result.getEnd());
            assertEquals(help.getPlaceId(), result.getPlaceId());
            assertEquals(help.getProgress().getClass().getSimpleName(), result.getProgress().getClass().getSimpleName());
            assertEquals(help.getReward(), result.getReward());

        }
    }

    @Nested
    class selectAllHelp {

        @DisplayName("PlaceId 없음. 문제 없음")
        @Test
        void noPlaceId() {
            //given
            List<String> ids = new ArrayList<String>();
            ids.add("API:1");

            //when
            List<Help> helps = sut.selectAllHelp(ids);

            //then
            assertEquals(0, helps.size());
        }

        @DisplayName("id = placeId로 같은 경우.문제 없음.")
        @Test
        void onePlaceId() {
            String placeId = "API:1";
            //given
            CheckIn check = CheckInFixture.createCheckInWithIdAndPlaceId(Created.create(), 1L, placeId);
            CheckIn check2 = CheckInFixture.createCheckInWithIdAndPlaceId(Created.create(), 2L, placeId);

            Long id = helpDBAdapter.save(check);
            Long id2 = helpDBAdapter.save(check2);

            List<String> ids = new ArrayList<String>();
            ids.add(placeId);

            //when
            List<Help> helps = sut.selectAllHelp(ids);

            //then
            assertEquals(2, helps.size());
            assertEquals(1L, helps.get(0).getId());
            assertEquals(2L, helps.get(1).getId());
        }

        @DisplayName("id에 PlaceID가 더 있는 경우. 문제 없음.")
        @Test
        void twoPlaceID() {
            String placeId = "API:1";
            //given
            CheckIn check = CheckInFixture.createCheckInWithIdAndPlaceId(Created.create(), 1L, placeId);
            CheckIn check2 = CheckInFixture.createCheckInWithIdAndPlaceId(Created.create(), 2L, placeId);

            Long id = helpDBAdapter.save(check);
            Long id2 = helpDBAdapter.save(check2);

            List<String> ids = new ArrayList<String>();
            ids.add(placeId);
            ids.add("API:2");

            //when
            List<Help> helps = sut.selectAllHelp(ids);

            //then
            assertEquals(2, helps.size());
            assertEquals(1L, helps.get(0).getId());
            assertEquals(2L, helps.get(1).getId());
            assertEquals("API:1", helps.get(0).getPlaceId());
            assertEquals("API:1", helps.get(1).getPlaceId());
        }


    }


}