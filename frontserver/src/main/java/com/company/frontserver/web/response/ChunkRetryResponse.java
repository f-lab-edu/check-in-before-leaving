package com.company.frontserver.web.response;

public class ChunkRetryResponse extends ExceptionResponse {

    private final int chunkNumber;

    public ChunkRetryResponse(String message, int chunkNumber) {
        super(message);
        this.chunkNumber = chunkNumber;
    }

    @Override
    public String toString() {
        return "ChunkUploadResponse{" +
                "message='" + getMessage() + "\n" +
                "chunkNumber=" + chunkNumber +
                '}';
    }
}
