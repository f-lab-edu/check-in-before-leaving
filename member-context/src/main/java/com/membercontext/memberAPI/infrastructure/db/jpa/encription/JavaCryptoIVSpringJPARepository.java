package com.membercontext.memberAPI.infrastructure.db.jpa.encription;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.vo.JavaCryptoIV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JavaCryptoIVSpringJPARepository extends JpaRepository<JavaCryptoIV, Long> {
}
