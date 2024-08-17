package com.example.checkinrequestMS.HelpAPI.domain.service.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.progress.ProgressException;
import com.example.checkinrequestMS.HelpAPI.infra.fileIO.PhotoSaver;
import com.example.checkinrequestMS.HelpAPI.infra.db.progress.ProgressJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressBusinessCRUDServiceTest {

    @InjectMocks
    private ProgressBusinessCRUDService sut;

    @Mock
    private PhotoSaver photoSaver;

    @Mock
    private ProgressJPARepository progressJPARepository;

    @Test
    @DisplayName("승인 - 정상적으로 승인 됨.")
    void approve_Success() {
        Long progressId = 1L;
        Progress progress = spy(Progress.class);
        given(progressJPARepository.findById(progressId)).willReturn(Optional.of(progress));

        //when
        sut.approveProgress(progressId);

        //then
        assertEquals(true, progress.isCompleted());
    }
    @Test
    @DisplayName("승인 - Progress 존재하지 않음.")
    void approve_No_Progress(){
        Long progressId = 0L;

        Exception exception = assertThrows(Exception.class, () -> sut.approveProgress(progressId));

        assertEquals("진행 정보가 존재하지 않습니다.", exception.getMessage());
        assertEquals(ProgressException.class, exception.getClass());
    }

    @Test
    @DisplayName("사진 추가 - 정상적으로 사진 추가 됨.")
    void addPhoto_Success() throws IOException {
        Long progressId = 1L;
        MultipartFile multipartFile = mock(MultipartFile.class);

        Progress progress = spy(Progress.class);
        given(progressJPARepository.findById(progressId)).willReturn(Optional.of(progress));
        given(photoSaver.saveOnePhoto(progressId, multipartFile)).willReturn("/user/" + progressId + "/photo.png");

        //when
        sut.addPhoto(1L, multipartFile);

        //then
        assertEquals(progress.getPhotoPath(), "/user/1/photo.png");
    }
    @Test
    @DisplayName("사진 추가 - Progress 존재하지 않음.")
    void addphoto_No_Progress(){
        Long progressId = 0L;
        MultipartFile file = mock(MultipartFile.class);

        //when
        Exception exception = assertThrows(Exception.class, () -> sut.addPhoto(progressId, file));

        //then
        assertEquals("진행 정보가 존재하지 않습니다.", exception.getMessage());
        assertEquals(ProgressException.class, exception.getClass());
    }

}