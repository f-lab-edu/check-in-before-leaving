package com.membercontext.memberAPI.infrastructure.encryption.AES;

import com.membercontext.memberAPI.infrastructure.encryption.exception.EncryptionRepositoryException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static com.membercontext.memberAPI.infrastructure.encryption.AES.ErrorCode.AESErrorCode.BYTE_SIZE_LENGTH_WRONG;


public class AESCryptoUtil{

    private final String encryptionAlgorithm = "AES";
    private String encryptionSpec;
    private String key;
    //ECB -> ID로 한번 암호화하면 같은 값이 나옴


    public AESCryptoUtil(String key, String encryptionSpec) {
        this.key = key;
        this.encryptionSpec = encryptionAlgorithm + encryptionSpec;
    }

    /**
     * 키 반환
     */
    private SecretKey getKey() {
        return new SecretKeySpec(key.getBytes(), 0, AESBytesizeCheck(key), encryptionAlgorithm);
    }
    /**
     * 초기화 벡터 반환
     */
    public byte[] getIv() {
        byte[] iv = new byte[AESBytesizeCheck(key)];
         new SecureRandom().nextBytes(iv);
         return iv;
    }

    private int AESBytesizeCheck(String key){
        if (key.getBytes().length != 16) throw new EncryptionRepositoryException(BYTE_SIZE_LENGTH_WRONG);
        return 16;
    }
    public String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(encryptionSpec);
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
    }
    public String encrypt(IvParameterSpec iv,
                          String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(encryptionSpec);
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), iv);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
    }

    public String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(encryptionSpec);
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }
    public String decrypt(IvParameterSpec iv,
                                 String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
        InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(encryptionSpec);
        cipher.init(Cipher.DECRYPT_MODE, getKey(), iv);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    //getInstance -> NoSuchPaddingException, NoSuchAlgorithmException
    //init -> InvalidAlgorithmParameterExceprtion, InvalidKeyException
    //doFinal -> IllegalBlockSizeException, BadPaddingException




}
