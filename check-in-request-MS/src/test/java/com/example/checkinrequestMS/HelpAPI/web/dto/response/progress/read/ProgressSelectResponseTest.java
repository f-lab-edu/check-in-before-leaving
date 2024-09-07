package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Authenticated;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Ongoing;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child.AuthenticatedSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child.CreatedSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child.OnGoingSelectResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProgressSelectResponseTest {

    static Stream<Arguments> progress() {
        return Stream.of(
                Arguments.of(Created.create(), CreatedSelectResponse.class.getSimpleName()),
                Arguments.of(Authenticated.of(1L, "photo", false), AuthenticatedSelectResponse.class.getSimpleName()),
                Arguments.of(Ongoing.from(1L), OnGoingSelectResponse.class.getSimpleName())

        );
    }

    @ParameterizedTest(name = "{index} - {1}")
    @MethodSource("progress")
    @DisplayName("Progress 객체에 따라 ProgressResponse 객체를 반환.")
    void getProgressDTO(Progress progress, String className) {

        //when
        ProgressSelectResponse sut = ProgressSelectResponse.getProgressDTO(progress);

        //then
        assertEquals(sut.getClass().getSimpleName(), className);
    }
}