package com.membercontext.memberAPI.infrastructure.encryption.AES.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AESErrorCode {



    BYTE_SIZE_LENGTH_WRONG("AES의 바이트 사이즈 길이는 16바이트이어야 합니다.");




    private final String deatil;
}
