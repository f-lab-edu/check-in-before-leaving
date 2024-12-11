package com.membercontext.memberAPI.infrastructure.encryption.aes256.exception;


import com.membercontext.memberAPI.infrastructure.encryption.aes256.exception.ErrorCode.JavaCryptoErrorCode;
import lombok.Getter;

@Getter

public class JavaCryptoException extends RuntimeException {
    private final JavaCryptoErrorCode javaCryptoCode;

    public JavaCryptoException(JavaCryptoErrorCode javaCryptoCode) {
        super(javaCryptoCode.getDeatil());
        this.javaCryptoCode = javaCryptoCode;
    }
}

