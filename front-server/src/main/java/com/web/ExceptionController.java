package com.web;

import com.frontServer.infra.ChunkUploadException;
import com.web.response.ChunkRetryResponse;
import com.web.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({
            ChunkUploadException.class
    })
    public ResponseEntity<ExceptionResponse> chunkUploadException(final ChunkUploadException e) {

        if (e.getException().isPresent())
            //로그
            return ResponseEntity.internalServerError().body(new ExceptionResponse("internal error"));
        else {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new ChunkRetryResponse("retry neede", e.getChunkNumber()));
        }
    }
}
