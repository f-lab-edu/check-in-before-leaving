package com.company.member.infrastructure.encryption.bcrypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BcryptEncoderTest {

    private final BycryptEncoder bycryptEncoder = new BycryptEncoder();

    @Test
    void encrypt_ShouldReturnHashedPassword() {
        // Given
        String password = "myPassword";

        // When
        String hashedPassword = bycryptEncoder.encrypt(password);

        // Then
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void checkPassword_ShouldReturnTrueForMatchingPassword() {
        // Given
        String password = "myPassword";
        String hashedPassword = bycryptEncoder.encrypt(password);

        // When
        boolean isMatch = bycryptEncoder.checkPassword(password, hashedPassword);

        // Then
        assertTrue(isMatch);
    }

    @Test
    void checkPassword_ShouldReturnFalseForNonMatchingPassword() {
        // Given
        String password = "myPassword";
        String hashedPassword = bycryptEncoder.encrypt(password);
        String wrongPassword = "wrongPassword";

        // When
        boolean isMatch = bycryptEncoder.checkPassword(wrongPassword, hashedPassword);

        // Then
        assertFalse(isMatch);
    }

}