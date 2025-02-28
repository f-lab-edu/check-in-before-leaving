package com.frontServer.infra.chunkUpload;

import com.frontServer.infra.chunkUpload.exceptions.ChunkRetryNeededException;
import com.frontServer.infra.chunkUpload.exceptions.ChunkUploadError;
import com.frontServer.infra.chunkUpload.exceptions.ChunkUploadRetryException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.frontServer.infra.chunkUpload.exceptions.ChunkUploadError.*;

@Component
public class ChunkValidator {

    public void chunkExistCheck(Path chunkPath) throws ChunkRetryNeededException {
        if (!Files.exists(chunkPath)) {
            throw new ChunkRetryNeededException(ChunkUploadError.CHUNK_FILE_NOT_EXISTS);
        }
    }

    public void chunkSizeCheck(Path chunkPath, MultipartFile chunk) throws ChunkRetryNeededException {

        try {
            long chunkSize = Files.size(chunkPath);
            if (chunkSize != chunk.getSize()) {
                throw new ChunkRetryNeededException(CHUNK_SIZE_NOT_MATCH);
            }
        } catch (IOException e) {
            throw new ChunkRetryNeededException(CHUNK_FILE_SIZE_IO_FAILED, e);
        }
    }

    public boolean isUploadComplete(Path chunkPathCommon, int chunkNumber, int totalChunks) {

        int lastChunk = totalChunks - 1;
        if (chunkNumber < lastChunk) {
            return false;
        }
        for (int i = 0; i < totalChunks; i++) {
            Path chunkPath = Paths.get(chunkPathCommon.toString() + i);
            if (!Files.exists(chunkPath)) {
                throw new ChunkUploadRetryException(UPLOAD_COMPETION_CHECK_FAILED, i);
            }
        }
        return true;
    }
}
