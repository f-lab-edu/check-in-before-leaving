package com.frontServer.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChunkValidatorTest {

    @InjectMocks
    private ChunkValidator chunkValidator;

    @Mock
    private MultipartFile multipartFile;

    @Test
    @DisplayName("청크 파일 존재 확인 - 존재하는 경우")
    void chunkExistCheck_FileExists() throws IOException, ChunkRetryException {
        // Given
        Path chunkPath = Paths.get("testfile.part0");
        Files.createFile(chunkPath);

        // When & Then
        assertDoesNotThrow(() -> chunkValidator.chunkExistCheck(chunkPath));

        // Clean up
        Files.delete(chunkPath);
    }

    @Test
    @DisplayName("청크 파일 존재 확인 - 존재하지 않는 경우")
    void chunkExistCheck_FileNotExists() {
        // Given
        Path chunkPath = Paths.get("testfile.part0");

        // When & Then
        assertThrows(ChunkRetryException.class, () -> chunkValidator.chunkExistCheck(chunkPath));
    }

    @Test
    @DisplayName("청크 파일 크기 확인 - 크기 일치")
    void chunkSizeCheck_SizeMatches() throws IOException, ChunkRetryException {
        // Given
        Path chunkPath = Paths.get("testfile.part0");
        byte[] fileContent = "test content".getBytes();
        Files.write(chunkPath, fileContent);

        when(multipartFile.getSize()).thenReturn((long) fileContent.length);

        // When & Then
        assertDoesNotThrow(() -> chunkValidator.chunkSizeCheck(chunkPath, multipartFile));

        // Clean up
        Files.delete(chunkPath);
    }

    @Test
    @DisplayName("청크 파일 크기 확인 - 크기 불일치")
    void chunkSizeCheck_SizeMismatch() throws IOException {
        // Given
        Path chunkPath = Paths.get("testfile.part0");
        byte[] fileContent = "test content".getBytes();
        Files.write(chunkPath, fileContent);

        when(multipartFile.getSize()).thenReturn((long) fileContent.length + 1);

        // When & Then
        assertThrows(ChunkRetryException.class, () -> chunkValidator.chunkSizeCheck(chunkPath, multipartFile));

        // Clean up
        Files.delete(chunkPath);
    }

    @Test
    @DisplayName("업로드 완료 확인 - 모든 청크 파일 존재")
    void isUploadComplete_AllChunksExist() throws IOException, ChunkRetryException {
        // Given
        Path chunkPathCommon = Paths.get("testfile.part");
        int totalChunks = 2;
        for (int i = 0; i < totalChunks; i++) {
            Files.createFile(Paths.get(chunkPathCommon.toString() + i));
        }

        // When & Then
        assertTrue(chunkValidator.isUploadComplete(chunkPathCommon, totalChunks - 1, totalChunks));

        // Clean up
        for (int i = 0; i < totalChunks; i++) {
            Files.delete(Paths.get(chunkPathCommon.toString() + i));
        }
    }

    @Test
    @DisplayName("업로드 완료 확인 - 일부 청크 파일 존재하지 않음")
    void isUploadComplete_SomeChunksMissing() throws IOException {
        // Given
        Path chunkPathCommon = Paths.get("testfile.part");
        int totalChunks = 2;
        Files.createFile(Paths.get(chunkPathCommon.toString() + 0));

        // When & Then
        assertThrows(ChunkRetryException.class, () -> chunkValidator.isUploadComplete(chunkPathCommon, totalChunks - 1, totalChunks));

        // Clean up
        Files.delete(Paths.get(chunkPathCommon.toString() + 0));
    }
}