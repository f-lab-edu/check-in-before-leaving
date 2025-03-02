package com.company.frontserver.infra.integrated;

import com.company.frontserver.infra.chunkUpload.ChunkValidator;
import com.company.frontserver.infra.chunkUpload.exceptions.ChunkRetryNeededException;
import com.company.frontserver.infra.chunkUpload.exceptions.ChunkUploadError;
import com.company.frontserver.infra.chunkUpload.exceptions.ChunkUploadRetryException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    private static final Path TEST_DIR = Paths.get("testfile_ChunkValidatorTest");

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
    @DisplayName("청크 파일 생성 확인 - 생성 됨.")
    void chunkExistCheck_FileExists() throws IOException {
        // Given
        Path chunkPath = TEST_DIR.resolve("testfile.part0");
        Files.createFile(chunkPath);

        // When & Then
        assertDoesNotThrow(() -> chunkValidator.chunkExistCheck(chunkPath));

        // Clean up
        Files.delete(chunkPath);
    }

    @Test
    @DisplayName("청크 파일 생성 확인(RetryNeeded) - 미생성")
    void chunkExistCheck_FileNotExists() {
        // Given
        Path chunkPath = TEST_DIR.resolve("testfile.part0");

        // When & Then
        ChunkRetryNeededException e = assertThrows(ChunkRetryNeededException.class, () -> chunkValidator.chunkExistCheck(chunkPath));
        assertEquals(ChunkUploadError.CHUNK_FILE_NOT_EXISTS, e.getErrorCode());
        assertTrue(e.getException().isEmpty());
    }

    @Test
    @DisplayName("청크 파일 크기 확인 - 크기 일치")
    void chunkSizeCheck_SizeMatches() throws IOException {
        // Given
        Path chunkPath = TEST_DIR.resolve("testfile.part0");
        byte[] fileContent = "test content".getBytes();
        Files.write(chunkPath, fileContent);

        when(multipartFile.getSize()).thenReturn((long) fileContent.length);

        // When & Then
        assertDoesNotThrow(() -> chunkValidator.chunkSizeCheck(chunkPath, multipartFile));

        // Clean up
        Files.delete(chunkPath);
    }


    @ParameterizedTest(name = "변경된 크기: {0}")
    @ValueSource(ints = {1, -1})
    @DisplayName("청크 파일 크기 확인(RetryNeeded) - 보낸 파일크기와 불일치")
    void chunkSizeCheck_SizeMismatch(int change) throws IOException {
        // Given
        Path chunkPath = Paths.get("testfile.part0");
        byte[] fileContent = "test content".getBytes();
        Files.write(chunkPath, fileContent);

        when(multipartFile.getSize()).thenReturn((long) fileContent.length + change);

        // When & Then
        ChunkRetryNeededException e = assertThrows(ChunkRetryNeededException.class, () -> chunkValidator.chunkSizeCheck(chunkPath, multipartFile));
        assertEquals(ChunkUploadError.CHUNK_SIZE_NOT_MATCH, e.getErrorCode());
        assertTrue(e.getException().isEmpty());

        // Clean up
        Files.delete(chunkPath);
    }

    @Test
    @DisplayName("청크 파일 크기 확인(RetryNeeded) - 파일 IO 에러")
    void chunkSizeCheck_SizeMismatch_IOException() throws IOException {
        // Given
        Path chunkPath = TEST_DIR.resolve("testfile.part0");
        byte[] fileContent = "test content".getBytes();

        ChunkRetryNeededException e;
        try (MockedStatic<Files> mockedFileChannel = mockStatic(Files.class)) {
            when(Files.size(chunkPath)).thenThrow(new IOException());

            //When
            e = assertThrows(ChunkRetryNeededException.class, () -> chunkValidator.chunkSizeCheck(chunkPath, multipartFile));
        }

        // Then
        assertEquals(ChunkUploadError.CHUNK_FILE_SIZE_IO_FAILED, e.getErrorCode());
        assertTrue(e.getException().isPresent());
    }


    @Test
    @DisplayName("업로드 완료 확인 - 모든 청크 파일 존재")
    void isUploadComplete_AllChunksExist() throws IOException {
        // Given
        Path chunkPathCommon = TEST_DIR.resolve("testfile.part");
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
    @DisplayName("업로드 완료 확인(UploadRetry) - 일부 청크 파일 존재하지 않음")
    void isUploadComplete_SomeChunksMissing() throws IOException {
        // Given
        Path chunkPathCommon = TEST_DIR.resolve("testfile.part");
        int totalChunks = 2;
        Files.createFile(Paths.get(chunkPathCommon.toString() + 0));

        // When & Then
        ChunkUploadRetryException e = assertThrows(ChunkUploadRetryException.class, () -> chunkValidator.isUploadComplete(chunkPathCommon, totalChunks - 1, totalChunks));
        assertEquals(ChunkUploadError.UPLOAD_COMPETION_CHECK_FAILED.toString(), e.getMessage());
        assertEquals(1, e.getChunkNumber());

        // Clean up
        Files.delete(Paths.get(chunkPathCommon.toString() + 0));
    }

}