package com.company.frontserver.infra.integrated;

import com.company.frontserver.infra.chunkUpload.ChunkValidator;
import com.company.frontserver.infra.chunkUpload.FileChunkCopyHelper;
import com.company.frontserver.infra.chunkUpload.FileChunkUploader;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class FileChunkUploaderTest {

    @Autowired
    private FileChunkUploader fileChunkUploader;

    private MultipartFile multipartFile;

    @SpyBean
    private ChunkValidator chunkValidator;

    @SpyBean
    private FileChunkCopyHelper fileChunkCopyHelper;

    @Value("${upload.dir.chunk}")
    private String chunkDirPath;

    @Value("${upload.dir.upload}")
    private String uploadDirPath;

    @BeforeEach
    void setUp() {
        final int fileSize = 100 * 1024 * 1024;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fileSize; i++) {
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

    @AfterEach
    void tearDown() throws IOException {
        File chunkDir = new File(chunkDirPath);
        if (chunkDir.exists()) {
            FileUtils.cleanDirectory(chunkDir);
            Files.deleteIfExists(chunkDir.toPath());
        }

        File uploadDir = new File(uploadDirPath);
        if (uploadDir.exists()) {
            FileUtils.cleanDirectory(uploadDir);
            Files.deleteIfExists(uploadDir.toPath());
        }
    }

    @Test
    @DisplayName("청크 업로드 완료")
    void chunkUploadCompleted() {

        // Given
        String filename = "testfile";
        int totalChunks = 1;
        int chunkNumber = 0;

        // When
        boolean result = fileChunkUploader.chunkUpload(multipartFile, chunkNumber, totalChunks, filename);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("청크 업로드 미완료")
    void chunkUploadNotCompleted() {
        // Given
        String filename = "testfile";
        int totalChunks = 2;
        int chunkNumber = 0;

        // When
        boolean result = fileChunkUploader.chunkUpload(multipartFile, chunkNumber, totalChunks, filename);

        // Then
        assertFalse(result);

    }


}