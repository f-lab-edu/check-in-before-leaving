// FileChunkCopyHelperTest.java
package com.frontServer.infra;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileChunkCopyHelperTest {

    @InjectMocks
    private FileChunkCopyHelper fileChunkCopyHelper;

    @Mock
    private MultipartFile multipartFile;

    private static final Path TEST_DIR = Paths.get("testfile_FileChunkCopyHelperTest");

    @AfterAll
    static void cleanUp() throws IOException {
        Files.deleteIfExists(TEST_DIR);
    }

    @Test
    @DisplayName("파일 복사 성공")
    void copyFile_Success() throws IOException, ChunkRetryException {
        // Given
        Path chunkPath = TEST_DIR.resolve("testfile.part001");
        byte[] fileContent = "test content".getBytes();
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

        // When & Then
        assertDoesNotThrow(() -> fileChunkCopyHelper.copyFile(chunkPath, multipartFile));
    }

    @Test
    @DisplayName("파일 복사 실패")
    void copyFile_Failure() throws IOException {
        // Given
        Path chunkPath = TEST_DIR.resolve("testfile.part0");
        when(multipartFile.getInputStream()).thenThrow(new IOException("Mocked IO Exception"));

        // When & Then
        assertThrows(ChunkRetryException.class, () -> fileChunkCopyHelper.copyFile(chunkPath, multipartFile));
    }

    @Test
    @DisplayName("청크 병합 성공")
    void mergeChunks_Success() throws IOException, ChunkRetryException {
        // Given
        Path chunkPathCommon = TEST_DIR.resolve("testfile.part");
        Path mergedFile = Paths.get("mergedfile");
        int totalChunks = 2;
        for (int i = 0; i < totalChunks; i++) {
            Files.createFile(Paths.get(chunkPathCommon.toString() + i));
        }

        // When & Then
        assertDoesNotThrow(() -> fileChunkCopyHelper.mergeChunks(mergedFile, chunkPathCommon, totalChunks));

    }

    @Test
    @DisplayName("청크 병합 실패")
    void mergeChunks_Failure() throws IOException {
        // Given
        Path chunkPathCommon = TEST_DIR.resolve("testfile.part");
        Path mergedFile = Paths.get("mergedfile");
        int totalChunks = 2;
        Files.createFile(Paths.get(chunkPathCommon.toString() + 0));

        // When & Then
        assertThrows(ChunkRetryException.class, () -> fileChunkCopyHelper.mergeChunks(mergedFile, chunkPathCommon, totalChunks));
    }

}