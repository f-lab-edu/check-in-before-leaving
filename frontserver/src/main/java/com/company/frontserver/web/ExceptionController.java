package com.company.frontserver.web;

import com.company.frontserver.infra.chunkUpload.exceptions.ChunkUploadRetryException;
import com.company.frontserver.infra.chunkUpload.exceptions.ChunkUploadTechnicalException;
import com.company.frontserver.web.response.ChunkRetryResponse;
import com.company.frontserver.web.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    private final static String INTERNAL_ERROR = "내부 에러가 발생했습니다.";
    private final static String RETRY_NEEDED = "전송 중 오류 발생으로 재시도 요청합니다.";

    @ExceptionHandler({
            ChunkUploadRetryException.class
    })
    public ResponseEntity<ExceptionResponse> chunkUploadException(final ChunkUploadRetryException e) {

        if (e.getException().isPresent()) {
            //로그
        }
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new ChunkRetryResponse(RETRY_NEEDED, e.getChunkNumber()));

    }

    @ExceptionHandler({
            ChunkUploadTechnicalException.class
    })
    public ResponseEntity<ExceptionResponse> chunkUploadException(final ChunkUploadTechnicalException e) {

        //로그
        return ResponseEntity.internalServerError().body(new ExceptionResponse("internal error"));

    }
}
