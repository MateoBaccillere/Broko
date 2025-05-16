package com.broko.app.wallet_service.exception;

public class BusinessException extends RuntimeException{
    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
}
