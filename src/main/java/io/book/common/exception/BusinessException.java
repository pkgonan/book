package io.book.common.exception;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(String code) {
        super(code);
    }

    public BusinessException(String code, Throwable cause) {
        super(code, cause);
    }
}