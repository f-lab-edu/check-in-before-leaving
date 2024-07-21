package com.membercontext.memberAPI.infrastructure.encryption;

import com.membercontext.memberAPI.infrastructure.encryption.db.JavaCryptoIVRepository;
import com.membercontext.memberAPI.infrastructure.encryption.db.vo.JavaCryptoIV;
import com.membercontext.memberAPI.infrastructure.encryption.exception.ErrorCode.JavaCryptoCode;
import com.membercontext.memberAPI.infrastructure.encryption.exception.JavaCryptoException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static com.membercontext.memberAPI.infrastructure.encryption.exception.ErrorCode.JavaCryptoCode.*;


@Component
public class JavaCryptoUtil {

    private String encryptionAlgorithm = "AES";

    private final String mode_ECB = "ECB";
    private final String mode_CBC = "CBC";
    private final String padding = "PKCS5Padding";

    @Value("${encryption.key}")
    private String key;
    //ECB -> ID로 한번 암호화하면 같은 값이 나옴

    @Autowired
    private JavaCryptoIVRepository javaCryptoIVRepository;

    public String encrypt(String password) {
        return encrypting(password, setMode(Cipher.ENCRYPT_MODE, null));
    }

    public String encrypt(String password, Long id) {
        byte[] iv = saveIv(id);
        return encrypting(password, setMode(Cipher.ENCRYPT_MODE, iv));
    }

    public String decrypt(String encryptedPassword) {
        return decrypting(encryptedPassword, setMode(Cipher.DECRYPT_MODE, null));
    }

    public String decrypt(String encryptedPassword, Long id) {
        byte[] iv = javaCryptoIVRepository.get(id).orElseThrow(
                () -> new JavaCryptoException(JavaCryptoCode.IV_NOT_FOUND)
        ).getIv();
        return decrypting(encryptedPassword, setMode(Cipher.DECRYPT_MODE, iv));
    }

    private SecretKey getKey() {
        return new SecretKeySpec(key.getBytes(), 0, byteSizeCheck(key), encryptionAlgorithm);
    }

    private byte[] getIv() {
        byte[] iv = new byte[byteSizeCheck(key)];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private byte[] saveIv(Long id) {
        byte[] iv = getIv();
        javaCryptoIVRepository.save(JavaCryptoIV.createJavaCryptoIV(id, iv));
        return iv;
    }

    private int byteSizeCheck(String key) {
        if (key.getBytes().length != 16) throw new JavaCryptoException(BYTE_SIZE_LENGTH_WRONG);
        return 16;
    }

    private Cipher setMode(int mode, byte[] iv) {
        try {
            Cipher cipher;
            if (iv != null) {
                cipher = Cipher.getInstance(encryptionAlgorithm + "/" + mode_CBC + "/" + padding);
                cipher.init(mode, getKey(), new IvParameterSpec(iv));
                return cipher;
            } else {
                cipher = Cipher.getInstance(encryptionAlgorithm + "/" + mode_ECB + "/" + padding);
                cipher.init(mode, getKey());
                return cipher;
            }
        } catch (InvalidAlgorithmParameterException e) {
            throw new JavaCryptoException(INVALID_ALGORITHM_PARAMETER);
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
            throw new JavaCryptoException(JavaCryptoCode.ILLEGAL_BLOCK_SIZE);
        } catch (BadPaddingException e) {
            throw new JavaCryptoException(JavaCryptoCode.BAD_PADDING);
        }
    }

    private String decrypting(String encryptedPassword, Cipher cipher) {
        try {
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException e) {
            throw new JavaCryptoException(JavaCryptoCode.ILLEGAL_BLOCK_SIZE);
        } catch (BadPaddingException e) {
            throw new JavaCryptoException(JavaCryptoCode.BAD_PADDING);
        }
    }

}

//getInstance -> NoSuchPaddingException, NoSuchAlgorithmException
//init -> InvalidAlgorithmParameterExceprtion, InvalidKeyException
//doFinal -> IllegalBlockSizeException, BadPaddingException



