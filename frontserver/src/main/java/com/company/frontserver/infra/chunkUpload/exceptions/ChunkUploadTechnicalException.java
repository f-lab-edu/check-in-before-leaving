package com.company.frontserver.infra.chunkUpload.exceptions;

public class ChunkUploadTechnicalException extends RuntimeException {

    public ChunkUploadTechnicalException(ChunkUploadError message, Exception exception) {
        super(message.toString(), exception);
    }


}
