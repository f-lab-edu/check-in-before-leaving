package com.membercontext.memberAPI.application.repository.encryption.javaCrypto.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

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
        if(iv.length != 16){
            throw new RuntimeException("IV size is wrong");
        }else if(id == 0){
            throw new RuntimeException("id is null");
        }

        return JavaCryptoIV.builder()
            .id(id)
            .iv(iv)
            .build();
    }

}
