package com.membercontext.memberAPI.infrastructure.encryption.aes256.db.entity;

import com.membercontext.memberAPI.infrastructure.encryption.aes256.exception.JavaCryptoException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

import static com.membercontext.memberAPI.infrastructure.encryption.aes256.exception.ErrorCode.JavaCryptoErrorCode.NO_ID_FOR_IV;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "java_crypto_iv")
public class JavaCryptoIVEntity {

    @Id
    private Long id;

    @Lob
    private byte[] iv;

    public static JavaCryptoIVEntity createJavaCryptoIV(Long id, byte[] iv) {
        if (id == null && id < 1) {
            throw new JavaCryptoException(NO_ID_FOR_IV);
        }

        return JavaCryptoIVEntity.builder()
                .id(id)
                .iv(iv)
                .build();
    }

}
