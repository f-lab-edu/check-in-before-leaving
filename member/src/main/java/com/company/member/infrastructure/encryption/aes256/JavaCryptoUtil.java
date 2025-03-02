package com.company.member.infrastructure.encryption.aes256;

import com.company.member.domain.model.member.PasswordEncoder;
import com.company.member.infrastructure.encryption.aes256.exception.ErrorCode.JavaCryptoErrorCode;
import com.company.member.infrastructure.encryption.aes256.exception.JavaCryptoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.company.member.infrastructure.encryption.aes256.exception.ErrorCode.JavaCryptoErrorCode.*;

@Component
public class JavaCryptoUtil implements PasswordEncoder {
    // IV(Initialization Vector, 초기화 백터) 미적용. (ECB 모드 사용 중)

    private final String encryptionAlgorithm = "AES";

    @Value("${encryption.key}")
    private String key;

    @Override
    public String encrypt(String password) {
        return encrypting(password, setMode(Cipher.ENCRYPT_MODE));
    }

    @Override
    public boolean checkPassword(String password, String encryptedPassword) {
        return password.equals(decrypt(encryptedPassword));
    }

    private String decrypt(String encryptedPassword) {
        return decrypting(encryptedPassword, setMode(Cipher.DECRYPT_MODE));
    }

    private SecretKey getKey() {
        return new SecretKeySpec(key.getBytes(), 0, byteSizeCheck(key), encryptionAlgorithm);
    }

    private int byteSizeCheck(String key) {
        if (key.getBytes().length != 16) throw new JavaCryptoException(BYTE_SIZE_LENGTH_WRONG);
        return 16;
    }

    private Cipher setMode(int mode) {
        try {
            Cipher cipher;
            String padding = "PKCS5Padding";

            String mode_ECB = "ECB";
            cipher = Cipher.getInstance(encryptionAlgorithm + "/" + mode_ECB + "/" + padding);
            cipher.init(mode, getKey());
            return cipher;
        } catch (NoSuchPaddingException e) {
            throw new JavaCryptoException(NO_SUCH_PADDING);
        } catch (NoSuchAlgorithmException e) {
            throw new JavaCryptoException(NO_SUCH_ALGORITHM);
        } catch (InvalidKeyException e) {
            throw new JavaCryptoException(INVALID_KEY);
        }
    }

    private String encrypting(String password, Cipher cipher) {
        try {
            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(encrypted));
        } catch (IllegalBlockSizeException e) {
            throw new JavaCryptoException(JavaCryptoErrorCode.ILLEGAL_BLOCK_SIZE);
        } catch (BadPaddingException e) {
            throw new JavaCryptoException(JavaCryptoErrorCode.BAD_PADDING);
        }
    }

    private String decrypting(String encryptedPassword, Cipher cipher) {
        try {
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException e) {
            throw new JavaCryptoException(JavaCryptoErrorCode.ILLEGAL_BLOCK_SIZE);
        } catch (BadPaddingException e) {
            throw new JavaCryptoException(JavaCryptoErrorCode.BAD_PADDING);
        }
    }

}

//getInstance -> NoSuchPaddingException, NoSuchAlgorithmException
//init -> InvalidAlgorithmParameterExceprtion, InvalidKeyException
//doFinal -> IllegalBlockSizeException, BadPaddingException



