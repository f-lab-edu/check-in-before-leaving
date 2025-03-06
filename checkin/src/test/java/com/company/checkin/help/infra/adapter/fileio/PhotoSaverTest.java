package com.company.checkin.help.infra.adapter.fileio;

import com.company.checkin.help.infra.exceptions.fileio.FileIOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PhotoSaverTest {

    @InjectMocks
    private PhotoSaver sut;

    //check: filePath 수정
    @Test
    @Disabled("통합이라 끔. 다시 작동 예정")
    @DisplayName("사진 저장 - 정상적인 사진 저장")
    void saveOnePhoto() throws IOException {
        Long progressId = 1L;
        MultipartFile photo = mock(MultipartFile.class);
        InputStream inputStream = mock(InputStream.class);

        when(photo.getInputStream()).thenReturn(inputStream);
        when(photo.getOriginalFilename()).thenReturn("image.png");

        //when
        String result = sut.saveOnePhoto(progressId, photo);

        //then
        assertEquals(System.getProperty("user.home") + "/photo_for_authentication/1/image.png", result);
    }

    @Test
    @DisplayName("사진 저장 - progressId가 null일 때")
    void saveOnePhoto_No_Progress_ID() {
        Long progressId = null;
        MultipartFile photo = mock(MultipartFile.class);

        //when
        Exception exception = assertThrows(Exception.class, () -> {
            sut.saveOnePhoto(progressId, photo);
        });

        //then
        assertEquals("파일 경로를 가져오는 중 null pointer 발생", exception.getMessage());
        assertEquals(FileIOException.class, exception.getClass());
        assertEquals(NullPointerException.class, exception.getCause().getClass());
    }

    @Test
    @DisplayName("사진 저장 - IO Exception 발생")
    void saveOnePhoto_IO_Exception() throws IOException {
        //given
        Long progressId = 1L;
        MultipartFile photo = mock(MultipartFile.class);

        given(photo.getInputStream()).willThrow(new IOException());
        given(photo.getOriginalFilename()).willReturn("test.png");

        //when
        Exception exception = assertThrows(Exception.class, () -> sut.saveOnePhoto(progressId, photo));

        //then
        assertEquals("사진 저장 중 IO 예외 발생", exception.getMessage());
        assertEquals(FileIOException.class, exception.getClass());
        assertEquals(IOException.class, exception.getCause().getClass());
    }

    @Test
    @DisplayName("사진 저장 - 파일 이름이 null일 때")
    void saveOnePhoto_No_File_Name() {
        //given
        Long progressId = 1L;
        MultipartFile photo = mock(MultipartFile.class);

        //when
        Exception exception = assertThrows(Exception.class, () -> sut.saveOnePhoto(progressId, photo));

        //then
        assertEquals("파일 이름을 가져오는 중 null pointer 발생", exception.getMessage());
        assertEquals(FileIOException.class, exception.getClass());
        assertEquals(NullPointerException.class, exception.getCause().getClass());
    }
}