package com.membercontext.memberAPI.infrastructure.encryption.exception;

import com.membercontext.memberAPI.infrastructure.encryption.AES.ErrorCode.AESErrorCode;
import lombok.Getter;
@Getter

public class EncryptionRepositoryException extends RuntimeException {
    private AESErrorCode aesErrorCode;

    public EncryptionRepositoryException(AESErrorCode aesErrorCode) {
        super(aesErrorCode.getDeatil());
        this.aesErrorCode = aesErrorCode;
    }
}

