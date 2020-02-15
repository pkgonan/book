package io.book.member.exception;

import io.book.common.exception.BusinessException;

public class NotLoginMemberException extends BusinessException {

    private static final String CODE = "Not.login.member";

    public NotLoginMemberException() {
        super(CODE);
    }
}
