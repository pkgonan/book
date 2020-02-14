package io.book.member.domain;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class PasswordEncoder {

    public static final org.springframework.security.crypto.password.PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String encode(final String password) {
        return ENCODER.encode(password);
    }
}