package io.book.member.exception;

import io.book.common.exception.BusinessException;

public class AlreadyJoinedMemberException extends BusinessException {

    private static final String CODE = "Already.joined.member";

    public AlreadyJoinedMemberException() {
        super(CODE);
    }
}