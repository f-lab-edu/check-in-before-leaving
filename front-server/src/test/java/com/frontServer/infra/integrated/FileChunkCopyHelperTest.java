// FileChunkCopyHelperTest.java
package com.frontServer.infra.integrated;

import com.frontServer.infra.chunkUpload.FileChunkCopyHelper;
import com.frontServer.infra.chunkUpload.exceptions.ChunkRetryNeededException;
import com.frontServer.infra.chunkUpload.exceptions.ChunkUploadRetryException;
import com.frontServer.infra.chunkUpload.exceptions.ChunkUploadTechnicalException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileChunkCopyHelperTest {

    @InjectMocks
    private FileChunkCopyHelper fileChunkCopyHelper;

    @Mock
    private MultipartFile multipartFile;

    private static final Path TEST_DIR = Paths.get("testfile_FileChunkCopyHelperTest");

    @BeforeAll
    static void setUp() throws IOException {
        if (!Files.exists(TEST_DIR)) {
            Files.createDirectory(TEST_DIR);
        }
    }

    @AfterAll
    static void cleanUp() throws IOException {
        if (Files.exists(TEST_DIR)) {
            File parentFile = TEST_DIR.toFile();
            FileUtils.cleanDirectory(parentFile);
            Files.deleteIfExists(TEST_DIR);
        }
    }

    @Test
    @DisplayName("파일 복사 성공")
    void copyFile_Success() throws IOException {
        // Given
        Path chunkPath = TEST_DIR.resolve("testfile.part001");
        byte[] fileContent = "test content".getBytes();
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

        // When & Then
        assertDoesNotThrow(() -> fileChunkCopyHelper.copyFile(chunkPath, multipartFile));
    }

    @Test
    @DisplayName("파일 복사 실패(RetryNeeded) - 파일 복사 중 IO 예외 발생")
    void copyFile_Failure() throws IOException {
        // Given
        Path chunkPath = TEST_DIR.resolve("testfile.part0");
        when(multipartFile.getInputStream()).thenThrow(new IOException("Mocked IO Exception"));

        // When & Then
        assertThrows(ChunkRetryNeededException.class, () -> fileChunkCopyHelper.copyFile(chunkPath, multipartFile));
    }

    @Test
    @DisplayName("청크 병합 성공")
    void mergeChunks_Success() throws IOException {
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
    @DisplayName("청크 병합 실패(Retry) - 병합 중 IO 예외 발생")
    void mergeChunks_mergeProcessFailure() throws IOException {
        // Given
        Path chunkPathCommon = TEST_DIR.resolve("testfile.part");
        Path mergedFile = Paths.get("mergedfile");
        int totalChunks = 2;
        Path chunk1 = Paths.get(chunkPathCommon.toString() + 0);
        Files.createFile(chunk1);

        // When & Then
        assertThrows(ChunkUploadRetryException.class, () -> fileChunkCopyHelper.mergeChunks(mergedFile, chunkPathCommon, totalChunks));

        // Clean up
        Files.deleteIfExists(chunk1);
    }

    @Test
    @DisplayName("청크 병합 실패(Technical) - 파일 삭제 실패")
    void mergeChunks_FiledeleteFailed_IOFailure() throws IOException {
        // Given
        Path chunkPathCommon = TEST_DIR.resolve("testfile.part");
        Path mergedFile = Paths.get("mergedfile");
        int totalChunks = 2;
        Path chunk1 = Paths.get(chunkPathCommon.toString() + 0);
        Path chunk2 = Paths.get(chunkPathCommon.toString() + 1);
        Files.createFile(chunk1);
        Files.createFile(chunk2);

        try (MockedStatic<Files> mockedFileChannel = mockStatic(Files.class)) {
            mockedFileChannel.when(() -> Files.delete(any(Path.class))).thenThrow(new IOException("Mocked IO Exception"));

            // When & Then
            assertThrows(ChunkUploadTechnicalException.class, () -> fileChunkCopyHelper.mergeChunks(mergedFile, chunkPathCommon, totalChunks));
        }

        // Clean up
        Files.deleteIfExists(chunk1);
        Files.deleteIfExists(chunk2);
    }

    @Test
    @DisplayName("청크 병합 실패(Technical) - 머지용 파일 채널 오픈 실패")
    void mergeChunks_MergeStart_IOFailure() {
        // Given
        Path chunkPathCommon = TEST_DIR.resolve("testfile.part");
        Path mergedFile = Paths.get("mergedfile");
        int totalChunks = 2;

        try (MockedStatic<FileChannel> mockedFileChannel = mockStatic(FileChannel.class)) {
            mockedFileChannel.when(() -> FileChannel.open(mergedFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE)).thenThrow(new IOException("Mocked IO Exception"));

            // When & Then
            assertThrows(ChunkUploadTechnicalException.class, () -> fileChunkCopyHelper.mergeChunks(mergedFile, chunkPathCommon, totalChunks));
        }

    }

}