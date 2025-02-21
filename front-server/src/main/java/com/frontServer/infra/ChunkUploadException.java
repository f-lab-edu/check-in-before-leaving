package com.frontServer.infra;

public class ChunkUploadException extends RuntimeException {


    private final int chunkNumber;

    public ChunkUploadException(String message, int chunkNumber) {
        super(message);
        this.chunkNumber = chunkNumber;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }


}
