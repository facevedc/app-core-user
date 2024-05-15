package com.app.core.user.domain.exceptions;

public class NotFoundErrorException extends RuntimeException{

    public NotFoundErrorException(String message) {
        super(message);
    }
}
