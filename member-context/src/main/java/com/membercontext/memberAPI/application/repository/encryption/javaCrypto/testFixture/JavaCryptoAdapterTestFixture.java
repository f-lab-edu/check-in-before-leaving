package com.membercontext.memberAPI.application.repository.encryption.javaCrypto.testFixture;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.JavaCryptoAdapter;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.javaCryptoRepository.JavaCryptoIVRepository;

public class JavaCryptoAdapterTestFixture extends JavaCryptoAdapter {

    public static JavaCryptoAdapter createJavaCryptoAdapter(String key, String algorithm, String mode, String padding, JavaCryptoIVRepository javaCryptoIVRepository) {
        return JavaCryptoAdapter.builder()
                .key(key)
                .algorithm(algorithm)
                .mode(mode)
                .padding(padding)
                .javaCryptoIVRepository(javaCryptoIVRepository)
                .build();
    }

}
