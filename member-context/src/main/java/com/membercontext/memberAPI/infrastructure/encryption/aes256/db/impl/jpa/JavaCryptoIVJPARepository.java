package com.membercontext.memberAPI.infrastructure.encryption.aes256.db.impl.jpa;


import com.membercontext.memberAPI.infrastructure.encryption.aes256.db.JavaCryptoIVRepository;
import com.membercontext.memberAPI.infrastructure.encryption.aes256.db.entity.JavaCryptoIVEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class JavaCryptoIVJPARepository implements JavaCryptoIVRepository {

    private final JavaCryptoIVSpringJPARepository javaCryptoIVSpringJPARepository;

    @Override
    public void save(JavaCryptoIVEntity javaCryptoIV) {
        javaCryptoIVSpringJPARepository.save(javaCryptoIV);
    }

    @Override
    public Optional<JavaCryptoIVEntity> get(Long id) {
        return javaCryptoIVSpringJPARepository.findById(id);

    }
}
