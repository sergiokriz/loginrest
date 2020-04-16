package com.concrete.login.exception.response;

import org.springframework.http.HttpStatus;

/**
 * Description: Page not found exception handler
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CSxRequestPageNotFoundException extends CSxHttpException {

    /**
     * Constructs the CCxRequestPageNotFoundException
     *
     * @param aMessage message to be returned with the http status code
     */
    public CSxRequestPageNotFoundException(String aMessage) {
        super(HttpStatus.NOT_FOUND, aMessage);
    }
}
