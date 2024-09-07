package com.example.checkinrequestMS.HelpAPI.application.service.help;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.infra.db.stub.HelpDBAdapterStub;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HelpSelectServiceTest {

    @InjectMocks
    private HelpSelectService helpSelectService;

    @Spy
    private HelpDBAdapterStub helpDBAdapter;

    @Nested
    class selectHelp {
        static Stream<Arguments> models() {
            return Stream.of(
                    Arguments.of(CheckInFixture.createCheckInWithId(Created.create(), 1L), "체크인")
            );
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("models")
        @DisplayName("체크인, 줄서기 조회 성공")
        void selectALLHelps(Help help, String name) {
            //given
            Long savedId = helpDBAdapter.save(help);

            //when
            Help result = helpSelectService.selectHelp(1L);

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
}