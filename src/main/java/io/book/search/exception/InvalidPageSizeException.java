package io.book.search.exception;

import io.book.common.exception.BusinessException;

public class InvalidPageSizeException extends BusinessException {

    private static final String CODE = "Invalid.page.size";

    public InvalidPageSizeException() {
        super(CODE);
    }
}
