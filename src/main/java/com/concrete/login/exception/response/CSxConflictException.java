package com.concrete.login.exception.response;

import org.springframework.http.HttpStatus;

public class CSxConflictException extends CSxHttpException {

    /**
     * Constructs the CCxConflictException
     *
     * @param aMessage message to be returned with the http status code
     */
    public CSxConflictException(String aMessage) {
        super(HttpStatus.CONFLICT, aMessage);
    }
}
