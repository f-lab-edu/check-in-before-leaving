package com.company.member.infrastructure.encryption.bcrypt;

import com.company.member.domain.model.member.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
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
