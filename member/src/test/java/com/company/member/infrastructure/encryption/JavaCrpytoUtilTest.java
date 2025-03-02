package com.company.member.infrastructure.encryption;


import com.company.member.infrastructure.encryption.aes256.JavaCryptoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JavaCrpytoUtilTest {

    @Spy
    private JavaCryptoUtil javaCryptoUtil;

    private String createKey(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(javaCryptoUtil, "key", createKey(16));
    }

    @Test
    void encrypt() {
        //given

        String password = "password";
        Long id = 1L;

        //when
        String encryptedText = javaCryptoUtil.encrypt(password);

        //then
        assertNotNull(encryptedText);
        assertNotEquals(password, encryptedText);
    }

    @Test
    void checkPassword() {
        //given
        String password = "password";
        Long id = 1L;
        String encryptedText = javaCryptoUtil.encrypt(password);

        //when
        boolean isMatch = javaCryptoUtil.checkPassword(password, encryptedText);

        //then
        assertTrue(isMatch);
    }
}


