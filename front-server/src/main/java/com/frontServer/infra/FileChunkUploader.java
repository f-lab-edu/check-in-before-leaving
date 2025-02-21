package com.frontServer.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Repository
public class FileChunkUploader {

    public static final Path CHUNK_DIR = Paths.get("temp-uploads");
    public static final Path UPLOAD_DIR = Paths.get("uploads");
    public static final String CHUNK_FILE_EXTENSION = ".part";

    private final ChunkValidator chunkValidator;

    private final FileChunkCopyHelper fileChunkCopyHelper;

    @Autowired
    public FileChunkUploader(ChunkValidator chunkValidator, FileChunkCopyHelper fileChunkCopyHelper) {
        this.chunkValidator = chunkValidator;
        this.fileChunkCopyHelper = fileChunkCopyHelper;
    }

    public boolean chunkUpload(MultipartFile chunk, int chunkNumber,
                               int totalChunks, String filename) {

        final boolean mergedAndCompleted = true;
        final boolean needMoreChunks = false;
        try {
            createChunkDir();
            //  filename = duplicationCheck(filename); -> 중복 파일명 처리 (객체 추가 후 캡슐화 하기, 서비스에 추가. 비즈니스 로직임.)
            Path chunkPathCommon = CHUNK_DIR.resolve(filename + CHUNK_FILE_EXTENSION);
            Path chunkPath = Paths.get(chunkPathCommon.toString() + chunkNumber);

            fileChunkCopyHelper.copyFile(chunkPath, chunk);
            chunkValidator.chunkExistCheck(chunkPath);
            chunkValidator.chunkSizeCheck(chunkPath, chunk);

            Path mergedPath = UPLOAD_DIR.resolve(filename);
            if (chunkValidator.isUploadComplete(chunkPathCommon, chunkNumber, totalChunks)) {
                createUploadsDir();
                fileChunkCopyHelper.mergeChunks(mergedPath, chunkPathCommon, totalChunks);
                return mergedAndCompleted;
            } else {
                return needMoreChunks;
            }
        } catch (ChunkRetryException e) {
            throw new ChunkUploadException(e.getMessage(), chunkNumber);
        } catch (IOException e) {
            throw new ChunkUploadException("Directory Creation Failed", chunkNumber);
        }
    }

    private void createChunkDir() throws IOException {
        if (!Files.exists(CHUNK_DIR)) {
            Files.createDirectories(CHUNK_DIR);
        }
    }

    private void createUploadsDir() throws IOException {
        if (!Files.exists(UPLOAD_DIR)) {
            Files.createDirectories(UPLOAD_DIR);
        }
    }
//
//    private String duplicationCheck(String filename) {
//        Path currentFile = Paths.get(UPLOADING_DIR_NAME, filename);
//        if (Files.exists(currentFile)) {
//            String newName = createDifferentNameForSameFile(currentFile);
//            return newName;
//        }
//        return filename;
//    }
//
//    private String createDifferentNameForSameFile(Path currentFile) {
//        String fileName = currentFile.getFileName().toString();
//        String baseName = fileName;
//        String extension = "";
//
//        // 확장자 있으면 분리
//        int dotIndex = fileName.lastIndexOf('.');
//        if (dotIndex > 0) {
//            baseName = fileName.substring(0, dotIndex);
//            extension = fileName.substring(dotIndex);
//        }
//
//        int count = countSameNameFiles(Paths.get(UPLOADING_DIR_NAME), baseName);
//
//        return baseName + "(" + count + ")" + extension;
//    }
//
//    private int countSameNameFiles(Path directory, String baseName) {
//        try {
//            return (int) Files.list(directory)
//                    .map(path -> path.getFileName().toString())
//                    .filter(name -> name.startsWith(baseName))
//                    .count();
//        } catch (IOException e) {
//            return 0;
//        }
//    }
}
