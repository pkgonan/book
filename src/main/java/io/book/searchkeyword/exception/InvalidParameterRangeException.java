package io.book.searchkeyword.exception;

import io.book.common.exception.BusinessException;

public class InvalidParameterRangeException extends BusinessException {

    private static final String CODE = "Invalid.parameter.range";

    public InvalidParameterRangeException() {
        super(CODE);
    }
}