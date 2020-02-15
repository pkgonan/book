package io.book.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CryptoTest {

    @Test
    void encode() {
        final String password = "password";
        final String encodedPassword = Crypto.encode(password);

        Assertions.assertNotEquals(password, encodedPassword);
    }

    @Test
    void checkPassword() {
        final String password = "password";
        Assertions.assertTrue(Crypto.checkPassword(password, Crypto.encode(password)));
    }
}