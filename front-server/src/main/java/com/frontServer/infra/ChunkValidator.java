package com.frontServer.infra;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class ChunkValidator {

    public void chunkExistCheck(Path chunkPath) throws ChunkRetryException {
        if (!Files.exists(chunkPath)) {
            throw new ChunkRetryException("need retry");
        }
    }

    public void chunkSizeCheck(Path chunkPath, MultipartFile chunk) throws ChunkRetryException {

        try {
            long chunkSize = Files.size(chunkPath);
            if (chunkSize != chunk.getSize()) {
                throw new ChunkRetryException("Chunk Size Check Failed");
            }
        } catch (IOException e) {
            throw new ChunkRetryException("Chunk Size Check Failed", e);
        }
    }

    public boolean isUploadComplete(Path chunkPathCommon, int chunkNumber, int totalChunks) throws ChunkRetryException {

        int lastChunk = totalChunks - 1;
        if (chunkNumber < lastChunk) {
            return false;
        }
        for (int i = 0; i < totalChunks; i++) {
            Path chunkPath = Paths.get(chunkPathCommon.toString() + i);
            if (!Files.exists(chunkPath)) {
                throw new ChunkRetryException("Completion Check Failed", i);
            }
        }
        return true;
    }
}
