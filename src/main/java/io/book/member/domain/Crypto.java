package io.book.member.domain;

import org.mindrot.jbcrypt.BCrypt;

public class Crypto {

    public static String encode(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(final String password, final String encodedPassword) {
        return BCrypt.checkpw(password, encodedPassword);
    }
}