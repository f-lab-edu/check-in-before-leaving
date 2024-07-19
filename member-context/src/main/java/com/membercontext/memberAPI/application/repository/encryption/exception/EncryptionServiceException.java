package com.membercontext.memberAPI.application.repository.encryption.exception;

import com.membercontext.memberAPI.infrastructure.encryption.AES.ErrorCode.AESErrorCode;

public class EncryptionServiceException extends RuntimeException{

    private EncryptionServiceErrorCode encryptionServiceErrorCode;

    public EncryptionServiceException(EncryptionServiceErrorCode encryptionServiceErrorCode) {
        super(encryptionServiceErrorCode.getDeatil());
        this.encryptionServiceErrorCode = encryptionServiceErrorCode;
    }
}
