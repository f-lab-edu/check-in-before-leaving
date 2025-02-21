package com.frontServer.infra;

public class ChunkRetryException extends Exception {
    public ChunkRetryException(String message) {
        super(message);
    }

    public ChunkRetryException(String message, Exception e) {
        super(message, e);
    }

    public ChunkRetryException(String message, int chunkNumber) {
        super(message);
    }
}
