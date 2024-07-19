package com.membercontext.memberAPI.application.repository.encryption.javaCrypto;

import com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceException;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.handlers.AESHandler;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.javaCryptoRepository.JavaCryptoIVRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.repository.encryption.exception.EncryptionServiceErrorCode.JAVA_CRYPTO_ALGORITHM_NOT_SUPPORTED;

@Service
@Getter
@Builder(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class JavaCryptoAdapter implements JavaCryptoEncryption {

    @Value("${encryption.key}")
    private String key;
    @Value("${encryption.algorithm}")
    private String algorithm;
    @Value("${encryption.mode}")
    private String mode;
    @Value("${encryption.padding}")
    private String padding;

    @Autowired
    private JavaCryptoIVRepository javaCryptoIVRepository;

    public JavaCryptoEncryption selectJavaEncryptionTool(String algorithm){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        switch(algorithm){
            case "AES":
                 return new AESHandler(key, algorithm, mode, padding, javaCryptoIVRepository);
            default:
                throw new EncryptionServiceException(JAVA_CRYPTO_ALGORITHM_NOT_SUPPORTED);
        }
    }

    @Override
    public String encrypt(Member member){
        JavaCryptoEncryption encryption = selectJavaEncryptionTool(algorithm);
        return encryption.encrypt(member);
    }

    @Override
    public String decrypt(Member member) {
        JavaCryptoEncryption encryption = selectJavaEncryptionTool(algorithm);
        return encryption.decrypt(member);
    }
}
