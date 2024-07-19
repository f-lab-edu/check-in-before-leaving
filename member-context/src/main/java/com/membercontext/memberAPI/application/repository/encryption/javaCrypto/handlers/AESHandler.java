package com.membercontext.memberAPI.application.repository.encryption.javaCrypto.handlers;

import com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceErrorCode;
import com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceException;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.JavaCryptoEncryption;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.javaCryptoRepository.JavaCryptoIVRepository;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.vo.JavaCryptoIV;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.AES.AESCryptoUtil;
import javax.crypto.spec.IvParameterSpec;

import static com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceErrorCode.*;


public class AESHandler implements JavaCryptoEncryption {


    private String key;

    private String algorithm;

    private String mode;

    private String padding;

    private AESCryptoUtil aesCryptoUtil;

    private final String slash = "/";
    private final String mode_ECB = "ECB";
    private final String mode_CBC = "CBC";


    private JavaCryptoIVRepository javaCryptoIVRepository;



    public AESHandler(String key, String algorithm, String mode, String padding, JavaCryptoIVRepository javaCryptoIVRepository) {

        this.padding = padding;
        this.mode = mode;
        this.algorithm = algorithm;
        this.key = key;

        String spec = slash + mode + slash + padding;
        this.aesCryptoUtil = new AESCryptoUtil(key, spec);
        this.javaCryptoIVRepository = javaCryptoIVRepository;

    }

    @Override
    public String encrypt(Member member) {
        String password = member.getPassword();

        try {
            if (mode.equals(mode_ECB)) {
                return aesCryptoUtil.encrypt(password);
            }else if(mode.equals(mode_CBC)){
                byte[] iv = aesCryptoUtil.getIv();
                javaCryptoIVRepository.save(JavaCryptoIV.createJavaCryptoIV(member.getId(), iv));
                return aesCryptoUtil.encrypt(new IvParameterSpec(iv), password);
            }else{
                throw new EncryptionServiceException(AES_NOT_SUPPORTING_TYPE);
            }
        }catch (Exception e){
            throw new EncryptionServiceException(ENCRYPTION_FAILED);
        }
    }

    @Override
    public String decrypt(Member member) {
        String password = member.getPassword();
        try{
            if(mode.equals(mode_ECB)){
                return aesCryptoUtil.decrypt(password);
            }
            else if(mode.equals(mode_CBC)){
                byte[] iv = javaCryptoIVRepository.get(member.getId()).get().getIv();
                return aesCryptoUtil.decrypt(new IvParameterSpec(iv), password);
            }else{
                throw new EncryptionServiceException(AES_NOT_SUPPORTING_TYPE);
            }
        }catch (Exception e){
            throw new EncryptionServiceException(DECRYPTION_FAILED);
        }
    }


}
