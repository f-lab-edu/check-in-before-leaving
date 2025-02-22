package com.frontServer.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

import static com.frontServer.infra.FileChunkUploader.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileChunkUploaderTest {

    @InjectMocks
    private FileChunkUploader fileChunkUploader;

    private MultipartFile multipartFile;

    @Spy
    private ChunkValidator chunkValidator;

    @Spy
    private FileChunkCopyHelper fileChunkCopyHelper;

    @BeforeEach
    void setUp() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100 * 1024 * 1024; i++) {
            sb.append('A');
        }
        byte[] fileContent = sb.toString().getBytes();
        multipartFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                fileContent
        );
    }

    @Test
    @DisplayName("청크 업로드 완료")
    void chunkUploadCompleted() throws IOException {
        // Given

        String filename = "testfile";
        int totalChunks = 1;
        int chunkNumber = 0;

        // When
        boolean result = fileChunkUploader.chunkUpload(multipartFile, chunkNumber, totalChunks, filename);

        // Then
        assertTrue(result);

        //Delete
        Files.delete(UPLOAD_DIR.resolve(filename));
    }

    @Test
    @DisplayName("청크 업로드 미완료")
    void chunkUploadNotCompleted() throws IOException, ChunkRetryException {
        // Given
        String filename = "testfile";
        int totalChunks = 2;
        int chunkNumber = 0;

        // When
        boolean result = fileChunkUploader.chunkUpload(multipartFile, chunkNumber, totalChunks, filename);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("도중 실패 - 청크 번호 포함 예외.")
    void chunkUploadFailed() throws IOException, ChunkRetryException {
        // Given
        String filename = "testfile";
        int totalChunks = 2;
        int chunkNumber = 1;

        when(chunkValidator.isUploadComplete(any(), anyInt(), anyInt())).thenThrow(new ChunkRetryException("Completion Check Failed"));

        // When
        ChunkUploadException e = assertThrows(ChunkUploadException.class, () -> fileChunkUploader.chunkUpload(multipartFile, chunkNumber, totalChunks, filename));
        assertEquals(e.getChunkNumber(), chunkNumber);
        assertEquals("Completion Check Failed", e.getMessage());
    }


}