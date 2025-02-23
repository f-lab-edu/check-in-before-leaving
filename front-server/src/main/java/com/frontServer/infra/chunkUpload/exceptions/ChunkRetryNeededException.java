package com.frontServer.infra.chunkUpload.exceptions;

import jakarta.annotation.Nullable;

import java.util.Optional;

public class ChunkRetryNeededException extends Exception {

    private ChunkUploadError errorCode;
    @Nullable
    private Exception exception;

    public ChunkRetryNeededException(ChunkUploadError errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public ChunkRetryNeededException(ChunkUploadError errorCode, Exception exception) {
        super(errorCode.toString());
        this.errorCode = errorCode;
        this.exception = exception;
    }

    public ChunkUploadError getErrorCode() {
        return errorCode;
    }

    public Optional<Exception> getException() {
        return Optional.ofNullable(exception);
    }
}
