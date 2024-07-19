package com.membercontext.memberAPI.application.repository.encryption;

import com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceErrorCode;
import com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceException;

import static com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceErrorCode.DECRYPTION_NOT_IMPLEMENTED;
import static com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceErrorCode.ENCRPYION_NOT_IMPLEMENTED;

public interface EncryptionTool {

    default String encrypt(String password){
        throw new EncryptionServiceException(ENCRPYION_NOT_IMPLEMENTED);
    };
    default String decrypt(String password){
        throw new EncryptionServiceException(DECRYPTION_NOT_IMPLEMENTED);
    };


}
