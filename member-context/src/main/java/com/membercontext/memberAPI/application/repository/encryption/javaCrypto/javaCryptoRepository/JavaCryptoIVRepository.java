package com.membercontext.memberAPI.application.repository.encryption.javaCrypto.javaCryptoRepository;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.vo.JavaCryptoIV;

import java.util.Optional;

public interface JavaCryptoIVRepository {

    public void save(JavaCryptoIV javaCryptoIV);
    public Optional<JavaCryptoIV> get(Long id);
}
