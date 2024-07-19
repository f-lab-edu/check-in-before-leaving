package com.membercontext.memberAPI.infrastructure.db.jpa.encription;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.javaCryptoRepository.JavaCryptoIVRepository;
import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.vo.JavaCryptoIV;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JavaCryptoIVJPARepository implements JavaCryptoIVRepository {

    private final JavaCryptoIVSpringJPARepository javaCryptoIVSpringJPARepository;

    @Override
    public void save(JavaCryptoIV javaCryptoIV) {
        javaCryptoIVSpringJPARepository.save(javaCryptoIV);
    }

    @Override
    public Optional<JavaCryptoIV> get(Long id) {
        return javaCryptoIVSpringJPARepository.findById(id);

    }
}
