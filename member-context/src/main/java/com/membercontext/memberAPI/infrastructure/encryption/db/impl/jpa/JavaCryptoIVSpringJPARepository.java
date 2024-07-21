package com.membercontext.memberAPI.infrastructure.encryption.db.impl.jpa;



import com.membercontext.memberAPI.infrastructure.encryption.db.vo.JavaCryptoIV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JavaCryptoIVSpringJPARepository extends JpaRepository<JavaCryptoIV, Long> {

}
