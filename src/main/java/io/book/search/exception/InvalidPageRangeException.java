package io.book.search.exception;

import io.book.common.exception.BusinessException;

public class InvalidPageRangeException extends BusinessException {

    private static final String CODE = "Invalid.page.range";

    public InvalidPageRangeException() {
        super(CODE);
    }
}
