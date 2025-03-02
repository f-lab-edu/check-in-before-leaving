//package com.example.checkinrequestMS.HelpAPI.web.controller.progress.dto.response.write;
//
//import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
//import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
//import com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie.ProgressWriteController;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ProgressSelectResponseTest {
//
//    @Test
//    @DisplayName("Progress 등록됨.")
//    void getProgressSelectResponse() {
//        //given
//        Progress progress = Progress.DEFAULT;
//
//        //when
//        ProgressWriteController.ProgressSelectResponse sut = ProgressWriteController.ProgressSelectResponse.getProgressSelectResponse(progress);
//
//        //then
//        assertEquals(sut.getStatus(), progress.getStatus());
//        assertEquals(sut.isCompleted(), progress.isCompleted());
//        assertEquals(sut.getHelperId(), null);
//        assertEquals(sut.getPhotoPath(), null);
//    }
//
//    @Test
//    @DisplayName("Progress 시작됨.")
//    void getProgressSelectResponse_HelperAdded() {
//        //given
//        Progress progress = Progress.DEFAULT;
//        progress = progress.registerHelper(1L);
//
//        //when
//        ProgressWriteController.ProgressSelectResponse sut = ProgressWriteController.ProgressSelectResponse.getProgressSelectResponse(progress);
//
//        //then
//        assertEquals(sut.getStatus(), progress.getStatus());
//        assertEquals(sut.isCompleted(), progress.isCompleted());
//        assertEquals(sut.getHelperId(), progress.getHelperId().get());
//        assertEquals(sut.getPhotoPath(), null);
//    }
//
//
//}
