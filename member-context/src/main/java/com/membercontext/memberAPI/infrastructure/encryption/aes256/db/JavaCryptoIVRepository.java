package com.membercontext.memberAPI.infrastructure.encryption.aes256.db;


import com.membercontext.memberAPI.infrastructure.encryption.aes256.db.entity.JavaCryptoIVEntity;

import java.util.Optional;

public interface JavaCryptoIVRepository {

    public void save(JavaCryptoIVEntity javaCryptoIV);

    public Optional<JavaCryptoIVEntity> get(Long id);
}
