package com.membercontext.memberAPI.infrastructure.encryption.db.impl.jpa;



import com.membercontext.memberAPI.infrastructure.encryption.db.JavaCryptoIVRepository;
import com.membercontext.memberAPI.infrastructure.encryption.db.vo.JavaCryptoIV;
import com.membercontext.memberAPI.infrastructure.encryption.exception.JavaCryptoException;
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
