package com.company.member.domain.model.member;

public interface PasswordEncoder {
    String encrypt(String password);

    boolean checkPassword(String password, String hashedPassword);
}
