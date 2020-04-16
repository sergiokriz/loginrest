package com.concrete.login.exception.response;

import org.springframework.http.HttpStatus;

/**
 * Description: Request timeout exception handler
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CSxRequestTimeoutException extends CSxHttpException {

    /**
     * Constructs the CCxRequestTimeoutException
     *
     * @param aMessage message to be returned with the http status code
     */
    public CSxRequestTimeoutException(String aMessage) {
        super(HttpStatus.REQUEST_TIMEOUT, aMessage);
    }

}
