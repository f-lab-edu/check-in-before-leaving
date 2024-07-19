package com.membercontext.memberAPI.application.repository.encryption.javaCrypto;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.javaCryptoRepository.JavaCryptoIVRepository;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.testFixture.JavaCryptoAdapterTestFixture;
import com.membercontext.memberAPI.domain.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class JavaCryptoAdapterTest {

    @Autowired
    JavaCryptoIVRepository javaCryptoIVRepository;

    @Test
    void encrypt_WithOut_IV() {
        //given
        String key = "pkl14nskhapj2n8j";
        String algorithm = "AES";
        String mode = "ECB";
        String padding = "PKCS5Padding";

        JavaCryptoAdapter sut =
                JavaCryptoAdapterTestFixture
                        .createJavaCryptoAdapter(key, algorithm, mode, padding, javaCryptoIVRepository);

        Member member = mock(Member.class);

        //when 1
        when(member.getId()).thenReturn(1L);
        when(member.getPassword()).thenReturn("password");
        String encrypted = sut.encrypt(member);

        //when2
        when(member.getPassword()).thenReturn(encrypted);
        String decrypted = sut.decrypt(member);

        //then
        System.out.println("encrypted : " + encrypted);
        assertEquals("password", decrypted);
    }

    @Test
    void encrypt_With_IV() {
        //given
        String key = "pkl14nskhapj2n8j";
        String algorithm = "AES";
        String mode = "CBC";
        String padding = "PKCS5Padding";

        JavaCryptoAdapter sut =
                JavaCryptoAdapterTestFixture
                        .createJavaCryptoAdapter(key, algorithm, mode, padding, javaCryptoIVRepository);

        Member member = mock(Member.class);

        //when 1
        when(member.getId()).thenReturn(1L);
        when(member.getPassword()).thenReturn("password");
        String encrypted = sut.encrypt(member);

        //when2
        when(member.getPassword()).thenReturn(encrypted);
        String decrypted = sut.decrypt(member);

        //then
        System.out.println("encrypted : " + encrypted);
        assertEquals("password", decrypted);
    }
}