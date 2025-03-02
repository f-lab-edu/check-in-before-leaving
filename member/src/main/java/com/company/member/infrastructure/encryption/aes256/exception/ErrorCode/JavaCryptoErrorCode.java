package com.company.member.infrastructure.encryption.aes256.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JavaCryptoErrorCode {

    JAVA_ENCRIPTION_ERROR("자바 암호화 에러"),
    JAVA_DECRIPTION_ERROR("자바 복호화 에러"),

    JAVA_CRYPTO_IV_NOT_FOUND("IV값이 DB에 존재하지 않습니다."),
    NO_ID_FOR_IV("IV값을 저장하기 위한 ID값이 존재하지 않습니다."),

    BYTE_SIZE_LENGTH_WRONG("키값의 바이트 사이즈 길이는 16바이트이어야 합니다."),
    BAD_PADDING("패딩이 올바르지 않습니다."),
    NO_SUCH_ALGORITHM("알고리즘이 존재하지 않습니다."),
    NO_SUCH_PADDING("패딩이 존재하지 않습니다."),
    INVALID_ALGORITHM_PARAMETER("알고리즘 파라미터가 올바르지 않습니다."),
    INVALID_KEY("키값이 올바르지 않습니다."),
    ILLEGAL_BLOCK_SIZE("블록 사이즈가 올바르지 않습니다."),
    IV_NOT_FOUND("IV값이 존재하지 않습니다. 비밀번호가 암호화 되어 있지 않을 수 있습니다.");


    private final String deatil;
}
