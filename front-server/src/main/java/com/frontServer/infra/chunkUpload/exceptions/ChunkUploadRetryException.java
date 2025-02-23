package com.frontServer.infra.chunkUpload.exceptions;

import jakarta.annotation.Nullable;

import java.util.Optional;

public class ChunkUploadRetryException extends RuntimeException {


    private final int chunkNumber;
    @Nullable
    private Exception exception;
    // 재시도로 해결 가능할만한 거는 이 Exception을 던진다.
    // 그렇지만 특정 횟수 후에는 프론트에서 다른 URL로 요청을 보내서 ChunkUploadTechnicalException을 던지기.

    public ChunkUploadRetryException(ChunkUploadError message, int chunkNumber) {
        super(message.toString());
        this.chunkNumber = chunkNumber;
    }

    public ChunkUploadRetryException(ChunkUploadError message, int chunkNumber, Exception exception) {
        super(message.toString());
        this.chunkNumber = chunkNumber;
        this.exception = exception;
    }

    public Optional<Exception> getException() {
        return Optional.of(exception);
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

}
