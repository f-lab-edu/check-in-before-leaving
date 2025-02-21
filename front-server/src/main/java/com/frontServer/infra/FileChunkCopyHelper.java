package com.frontServer.infra;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;

@Component
public class FileChunkCopyHelper {

    public void copyFile(Path chunkPath, MultipartFile chunk) throws ChunkRetryException {
        try {
            Files.copy(chunk.getInputStream(), chunkPath, StandardCopyOption.REPLACE_EXISTING);
            waitCopying(chunkPath);
        } catch (IOException e) {
            throw new ChunkRetryException("Copy Failed", e);
        }
    }

    private void waitCopying(Path chunkPath) throws ChunkRetryException {
        final int maxAttempts = 50;
        int attempts = 0;

        while (!Files.exists(chunkPath)) {
            try {
                Thread.sleep(100);
                attempts++;
            } catch (InterruptedException e) {
                //fixme: 커스텀 예외 로직 만들기.
                throw new ChunkRetryException("Thread interrupted", e);
            }

            if (attempts >= maxAttempts) {
                throw new ChunkRetryException("Time Out");
            }
        }
    }

    public void mergeChunks(Path mergedFile, Path chunkPathCommon, int totalChunks) throws ChunkRetryException {

        try (FileChannel output = FileChannel.open(mergedFile,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            for (int i = 0; i < totalChunks; i++) {
                Path checkingPath = Paths.get(chunkPathCommon.toString() + i);

                try (FileChannel input = FileChannel.open(checkingPath, StandardOpenOption.READ)) {
                    input.transferTo(0, input.size(), output);
                } catch (IOException e) {
                    throw new ChunkRetryException("Merge Failed", e);
                }

                Files.delete(checkingPath);
            }
        } catch (IOException e) {
            throw new ChunkRetryException("Merge IO Failed", e);
        }
    }

}
