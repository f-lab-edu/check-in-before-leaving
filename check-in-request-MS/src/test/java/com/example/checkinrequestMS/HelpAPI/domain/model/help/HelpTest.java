package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.ProgressException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class HelpTest {


    static Stream<Arguments> objs() {
        return Stream.of(
                Arguments.of(CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L), "CheckIn"),
                Arguments.of(LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L), "LineUp"),
                Arguments.of(EtcFixture.createEtcWithId(Progress.DEFAULT, 1L), "Etc")
        );
    }

    static Stream<Arguments> objsOngoing() {
        CheckIn checkInOngoing = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);
        checkInOngoing.start(1L);

        LineUp lineUpOngoing = LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L);
        lineUpOngoing.start(1L);

        Etc etcOngoing = EtcFixture.createEtcWithId(Progress.DEFAULT, 1L);
        etcOngoing.start(1L);

        return Stream.of(
                Arguments.of(checkInOngoing, "CheckIn - ONGOING"),
                Arguments.of(lineUpOngoing, "LineUp - ONGOING"),
                Arguments.of(etcOngoing, "Etc - ONGOING")
        );
    }

    @MethodSource("objs")
    @ParameterizedTest(name = "{index} - {1}")
    @DisplayName("도움 생성")
    void create(Help sut, String testName) {

        assertEquals(Progress.ProgressStatus.CREATED, sut.getProgress().getStatus());
        assertEquals(Optional.empty(), sut.getProgress().getHelperId());
        assertEquals(Optional.empty(), sut.getProgress().getPhotoPath());
        assertEquals(false, sut.getProgress().isCompleted());
    }


    @MethodSource("objs")
    @ParameterizedTest(name = "{index} - {1}")
    @DisplayName("도움 시작")
    void start(Help sut, String testName) {
        //given
        Long helperId = 1L;

        //when
        sut.start(helperId);

        //then
        assertEquals(helperId, sut.getProgress().getHelperId().get());
        assertEquals(Progress.ProgressStatus.ONGOING, sut.getProgress().getStatus());
        assertEquals(Optional.empty(), sut.getProgress().getPhotoPath());
        assertEquals(false, sut.getProgress().isCompleted());
    }

    @MethodSource("objs")
    @ParameterizedTest(name = "{index} - {1}")
    @DisplayName("도움 인증")
    void authenticate(Help sut, String testName) {
        //given
        String photoPath = "photoPath";
        Long helperId = 1L;
        sut.start(helperId);

        //when
        sut.authenticate(photoPath);

        //then
        assertEquals(helperId, sut.getProgress().getHelperId().get());
        assertEquals(Progress.ProgressStatus.AUTHENTICATED, sut.getProgress().getStatus());
        assertEquals(photoPath, sut.getProgress().getPhotoPath().get());
        assertEquals(false, sut.getProgress().isCompleted());
    }

    @MethodSource("objs")
    @ParameterizedTest(name = "{index} - {1}")
    @DisplayName("도움 인증 실패")
    void authenticateFail(Help sut, String testName) {
        //given
        String photoPath = "photoPath";

        //when
        Exception exception = assertThrows(ProgressException.class, () -> sut.authenticate(photoPath));

        //then
        assertEquals(ProgressException.NOT_ONGOING, exception.getMessage());
    }

    @MethodSource("objs")
    @ParameterizedTest(name = "{index} - {1}")
    @DisplayName("도움 완료")
    void approve(Help sut, String testName) {

        //given
        Long helperId = 1L;
        String photoPath = "photoPath";
        sut.start(helperId);
        sut.authenticate(photoPath);

        //when
        sut.approve();

        //then
        assertEquals(Progress.ProgressStatus.COMPLETED, sut.getProgress().getStatus());
        assertEquals(true, sut.getProgress().isCompleted());
    }

    @MethodSource("objs")
    @ParameterizedTest(name = "{index} - {1}")
    @DisplayName("도움 완료 실패 - 시작되지 않음")
    void approveFail(Help sut, String testName) {

        //when
        Exception exception = assertThrows(ProgressException.class, () -> sut.approve());

        //then
        assertEquals(ProgressException.NOT_ONGOING, exception.getMessage());
    }

    @MethodSource("objsOngoing")
    @ParameterizedTest(name = "{index} - {1}")
    @DisplayName("도움 완료 실패 - 인증되지 않음")
    void approveFail2(Help sut, String testName) {
        //given

        //when
        Exception exception = assertThrows(ProgressException.class, () -> sut.approve());

        //then
        assertEquals(ProgressException.NOT_AUTHENTICATED, exception.getMessage());
    }
}