package com.membercontext.memberAPI.infrastructure.encryption;

import com.membercontext.memberAPI.infrastructure.encryption.AES.AESCryptoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AESCrpytoUtilTest {

    private final String keyString = "pkl14nskhapj2n8j";
    private AESCryptoUtil aesCryptoUtil;


    @Test
    void encrypt_With_IV() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //given
        aesCryptoUtil = new AESCryptoUtil(keyString, "/CBC/PKCS5Padding");
        String password = "password";
        byte[] iv = aesCryptoUtil.getIv();

        //when
        String encryptedText = aesCryptoUtil.encrypt(new IvParameterSpec(iv), password);
        String decryptedText = aesCryptoUtil.decrypt(new IvParameterSpec(iv), encryptedText);

        System.out.println("cipherText: " + encryptedText);
        assertEquals(password, decryptedText);
    }

    @Test
    void encrypt_WithOut_IV() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //given
        aesCryptoUtil = new AESCryptoUtil(keyString, "/ECB/PKCS5Padding");
        String password = "password";

        //when
        String encryptedText = aesCryptoUtil.encrypt(password);
        String decryptedText = aesCryptoUtil.decrypt(encryptedText);

        //result
        System.out.println("cipherText: " + encryptedText);
        assertEquals(password, decryptedText);
    }
}