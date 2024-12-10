package com.membercontext.memberAPI.domain.repository;

public interface PasswordEncoder {
    String encrypt(String password);

    boolean checkPassword(String password, String hashedPassword);
}
