package com.membercontext.memberAPI.infrastructure.db.jpa.encription;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.javaCryptoRepository.JavaCryptoIVRepository;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.vo.JavaCryptoIV;
import com.membercontext.memberAPI.infrastructure.encryption.AES.AESCryptoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JavaCryptoIVJPARepositoryTest {

    @Autowired
    JavaCryptoIVRepository javaCryptoIVSpringJPARepository;

    @Test
    void save() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        AESCryptoUtil aes = new AESCryptoUtil("pkl14nskhapj2n8j", "/CBC/PKCS5Padding");
        byte[] ivOriginal = aes.getIv();
        JavaCryptoIV javaCryptoIV = JavaCryptoIV.createJavaCryptoIV(1L, ivOriginal);

        String password = "test";
        String result = aes.encrypt(new IvParameterSpec(ivOriginal), password);


        javaCryptoIVSpringJPARepository.save(javaCryptoIV);
        JavaCryptoIV iv = javaCryptoIVSpringJPARepository.get(javaCryptoIV.getId()).get();

        String result2 = aes.decrypt(new IvParameterSpec(iv.getIv()), result);
        System.out.println(result);
        System.out.println(result2);
        assertTrue(Arrays.equals(javaCryptoIV.getIv(), iv.getIv()));

    }

}