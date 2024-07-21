package com.membercontext.memberAPI.infrastructure.encryption.db;




import com.membercontext.memberAPI.infrastructure.encryption.db.vo.JavaCryptoIV;

import java.util.Optional;

public interface JavaCryptoIVRepository {

    public void save(JavaCryptoIV javaCryptoIV);
    public Optional<JavaCryptoIV> get(Long id);
}
