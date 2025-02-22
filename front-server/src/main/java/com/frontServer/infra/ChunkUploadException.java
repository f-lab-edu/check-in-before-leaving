package com.frontServer.infra;

import jakarta.annotation.Nullable;

import java.util.Optional;

public class ChunkUploadException extends RuntimeException {


    private final int chunkNumber;
    @Nullable
    private Exception exception;

    public ChunkUploadException(String message, int chunkNumber) {
        super(message);
        this.chunkNumber = chunkNumber;
        this.exception = exception;
    }

    public ChunkUploadException(String message, int chunkNumber, Exception exception) {
        super(message);
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
