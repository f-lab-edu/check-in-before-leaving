package com.membercontext.memberAPI.infrastructure.encryption.exception;




import com.membercontext.memberAPI.infrastructure.encryption.exception.ErrorCode.JavaCryptoCode;
import lombok.Getter;
@Getter

public class JavaCryptoException extends RuntimeException {
    private JavaCryptoCode javaCryptoCode;

    public JavaCryptoException(JavaCryptoCode javaCryptoCode) {
        super(javaCryptoCode.getDeatil());
        this.javaCryptoCode = javaCryptoCode;
    }
}

