package io.book.member.exception;

import io.book.common.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {

    private static final String CODE = "Invalid.password";

    public InvalidPasswordException() {
        super(CODE);
    }
}
