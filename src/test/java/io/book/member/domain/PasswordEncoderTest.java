package io.book.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordEncoderTest {

    @Test
    void encode() {
        final String password = "password";
        final String encodedPassword = PasswordEncoder.encode(password);

        Assertions.assertNotEquals(password, encodedPassword);
    }
}