package com.membercontext.memberAPI.application.repository.encryption.javaCrypto.handlers;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.javaCryptoRepository.JavaCryptoIVRepository;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.vo.JavaCryptoIV;
import com.membercontext.memberAPI.domain.entity.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AESCrpytoHandlerTest {

    private AESHandler aesHandler;

    @Autowired
    private JavaCryptoIVRepository javaCryptoIVRepository;

    @Test
    void encrypt_With_IV() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //given
        String modeCBC = "CBC";
        aesHandler =  new AESHandler("pkl14nskhapj2n8j", "AES", modeCBC, "PKCS5Padding", javaCryptoIVRepository);
        String password = "password";
        Member member = mock(Member.class);

        //when
        when(member.getPassword()).thenReturn(password);
        when(member.getId()).thenReturn(1L); //DBd에 IV 저장하기 위한 ID
        String encryptedText = aesHandler.encrypt(member);

        when(member.getPassword()).thenReturn(encryptedText);
        String decryptedText = aesHandler.decrypt(member);

        //then
        System.out.println("cipherText: " + encryptedText);
        assertEquals(password, decryptedText);
    }

    @Test
    void encrypt_WithOut_IV() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //given
        String modeECB = "ECB";
        aesHandler =  new AESHandler("pkl14nskhapj2n8j", "AES", modeECB, "PKCS5Padding", javaCryptoIVRepository);
        String password = "password";
        Member member = mock(Member.class);

        //when
        when(member.getPassword()).thenReturn(password);
        String encryptedText = aesHandler.encrypt(member);

        when(member.getPassword()).thenReturn(encryptedText);
        String decryptedText = aesHandler.decrypt(member);

        //then
        System.out.println("cipherText: " + encryptedText);
        assertEquals(password, decryptedText);
    }


}