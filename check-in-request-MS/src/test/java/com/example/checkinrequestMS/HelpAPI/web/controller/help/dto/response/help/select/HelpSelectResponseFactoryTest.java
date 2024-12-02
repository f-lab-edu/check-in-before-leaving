package com.example.checkinrequestMS.HelpAPI.web.controller.help.dto.response.help.select;

import com.example.checkinrequestMS.HelpAPI.application.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.application.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HelpSelectResponseFactoryTest {

    @Nested
    @DisplayName("HelpSelectResponseFactory")
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
        @DisplayName("HelpSelectResponseFactory from 성공")
        void from(Help help, String className) {

            //when
            HelpSelectController.HelpSelectResponse sut = HelpSelectController.HelpSelectResponseFactory.from(help);

            //then
            assertEquals(sut.getClass().getSimpleName(), className);
        }

        @Test
        @DisplayName("HelpSelectResponseFactory from 에러")
        void error() {
            //given
            Help mockHelp = mock(Help.class);

            // when
            Exception result = assertThrows(HelpException.class, () -> {
                HelpSelectController.HelpSelectResponseFactory.from(mockHelp);
            });

            //then
            assertEquals(result.getMessage(), HelpErrorCode.HELP_RESPONSE_CREATION_ERROR.getDetail());
        }
    }
}
