package com.membercontext.memberAPI.application.repository.encryption.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncryptionServiceErrorCode {

    ENCRPYION_NOT_IMPLEMENTED("암호화 미구현"),
    DECRYPTION_NOT_IMPLEMENTED("복호화 미구현"),

    ENCRYPTION_FAILED("암호화 실패"),
    DECRYPTION_FAILED("복호화 실패"),

    JAVA_CRYPTO_ALGORITHM_NOT_SUPPORTED("지원하지 않는 JAVA 알고리즘 입니다."),

    AES_NOT_SUPPORTING_TYPE("지원하지 않는 AES 타입 입니다.");



    private final String deatil;
}
