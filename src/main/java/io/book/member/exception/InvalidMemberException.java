package io.book.member.exception;

import io.book.common.exception.BusinessException;

public class InvalidMemberException extends BusinessException {

    private static final String CODE = "Invalid.member";

    public InvalidMemberException() {
        super(CODE);
    }
}
