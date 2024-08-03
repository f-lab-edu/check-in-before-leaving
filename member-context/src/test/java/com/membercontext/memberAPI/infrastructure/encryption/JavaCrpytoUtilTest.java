package com.membercontext.memberAPI.infrastructure.encryption;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JavaCrpytoUtilTest {


    @Autowired
    private JavaCryptoUtil javaCryptoUtil;

    //check: 단위 테스트 작성.
    @Test
    void encrypt_With_IV(){
        //given
       // ReflectionTestUtils.setField(javaCryptoUtil, "encryptionSpec", "AES/CBC/PKCS5Padding");
        String password = "password";
        Long id = 1L;

        //when
        String encryptedText = javaCryptoUtil.encrypt(password, id);
        String decryptedText = javaCryptoUtil.decrypt(encryptedText, id);

        System.out.println("cipherText: " + encryptedText);
        assertEquals(password, decryptedText);
    }

    @Test
    void encrypt_WithOut_IV() {
        //given
        //ReflectionTestUtils.setField(javaCryptoUtil, "encryptionSpec", "AES/ECB/PKCS5Padding");
        String password = "password";

        //when
        String encryptedText = javaCryptoUtil.encrypt(password);
        String decryptedText = javaCryptoUtil.decrypt(encryptedText);

        //result
        System.out.println("cipherText: " + encryptedText);
        assertEquals(password, decryptedText);
    }
}