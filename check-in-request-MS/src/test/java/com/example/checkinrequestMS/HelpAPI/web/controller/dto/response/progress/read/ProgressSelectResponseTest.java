package com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.progress.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgressSelectResponseTest {


    @Test
    @DisplayName("Progress 객체에 따라 ProgressResponse 객체를 반환.")
    void getProgressSelectResponse() {

        //when
        ProgressSelectResponse sut = ProgressSelectResponse.getProgressSelectResponse(Progress.DEFAULT);

        //then
        assertEquals(Progress.ProgressStatus.CREATED, sut.getStatus());
        assertEquals(Optional.empty(), sut.getHelperId());
        assertEquals(Optional.empty(), sut.getPhotoPath());
    }
}