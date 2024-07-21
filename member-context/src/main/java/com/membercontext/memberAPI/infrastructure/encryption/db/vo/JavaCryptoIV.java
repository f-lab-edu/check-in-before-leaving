package com.membercontext.memberAPI.infrastructure.encryption.db.vo;

import com.membercontext.memberAPI.infrastructure.encryption.exception.JavaCryptoException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

import static com.membercontext.memberAPI.infrastructure.encryption.exception.ErrorCode.JavaCryptoCode.NO_ID_FOR_IV;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JavaCryptoIV {

    @Id
    private Long id;

    @Lob
    private byte[] iv;

    public static JavaCryptoIV createJavaCryptoIV(Long id, byte[] iv) {
        if(id == null && id < 1){
            throw new JavaCryptoException(NO_ID_FOR_IV);
        }

        return JavaCryptoIV.builder()
            .id(id)
            .iv(iv)
            .build();
    }

}
