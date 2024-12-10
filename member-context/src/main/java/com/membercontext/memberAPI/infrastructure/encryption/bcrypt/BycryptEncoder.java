package com.membercontext.memberAPI.infrastructure.encryption.bcrypt;

import com.membercontext.memberAPI.domain.repository.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BycryptEncoder implements PasswordEncoder {
    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }


}
