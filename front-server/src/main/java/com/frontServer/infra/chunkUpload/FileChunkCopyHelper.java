package com.frontServer.infra.chunkUpload;

import com.frontServer.infra.chunkUpload.exceptions.ChunkRetryNeededException;
import com.frontServer.infra.chunkUpload.exceptions.ChunkUploadRetryException;
import com.frontServer.infra.chunkUpload.exceptions.ChunkUploadTechnicalException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;

import static com.frontServer.infra.chunkUpload.exceptions.ChunkUploadError.*;

@Component
public class FileChunkCopyHelper {

    public void copyFile(Path chunkPath, MultipartFile chunk) throws ChunkRetryNeededException {
        try {
            Files.copy(chunk.getInputStream(), chunkPath, StandardCopyOption.REPLACE_EXISTING);
            waitCopying(chunkPath);
        } catch (IOException e) {
            throw new ChunkRetryNeededException(FILE_COPY_FAILED, e);
        }
    }

    private void waitCopying(Path chunkPath) throws ChunkRetryNeededException {
        final int maxAttempts = 50;
        int attempts = 0;

        while (!Files.exists(chunkPath)) {
            try {
                Thread.sleep(100);
                attempts++;
            } catch (InterruptedException e) {
                throw new ChunkRetryNeededException(WAIT_COPYING_INTERRUPTED, e);
            }

            if (attempts >= maxAttempts) {
                throw new ChunkRetryNeededException(WAIT_COPYING_TIME_OUT);
            }
        }
    }

    public void mergeChunks(Path mergedFile, Path chunkPathCommon, int totalChunks) throws ChunkRetryNeededException {

        try (FileChannel output = FileChannel.open(mergedFile,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            for (int i = 0; i < totalChunks; i++) {
                Path checkingPath = Paths.get(chunkPathCommon.toString() + i);

                try (FileChannel input = FileChannel.open(checkingPath, StandardOpenOption.READ)) {
                    final long fromHere = 0;
                    input.transferTo(fromHere, input.size(), output);
                } catch (IOException exception) {
                    throw new ChunkUploadRetryException(MERGE_FAILED, i, exception);
                }
                Files.delete(checkingPath);
            }
        } catch (IOException e) {
            throw new ChunkUploadTechnicalException(MERGE_IO_FAILED, e);
        }
    }

}
