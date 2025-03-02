package com.company.member.infrastructure.encryption.aes256.db.impl.jpa;


import com.company.member.infrastructure.encryption.aes256.db.entity.JavaCryptoIVEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JavaCryptoIVSpringJPARepository extends JpaRepository<JavaCryptoIVEntity, Long> {

}
