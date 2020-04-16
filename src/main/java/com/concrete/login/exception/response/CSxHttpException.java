package com.concrete.login.exception.response;

import org.springframework.http.HttpStatus;

/**
 * Description: Http Exception handler
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CSxHttpException extends RuntimeException {

    HttpStatus httpStatus;
    String message;

    /**
     * Constructs the CCxHttpException
     *
     * @param aHttpStatus http status code
     * @param aMessage    error message
     */
    public CSxHttpException(HttpStatus aHttpStatus, String aMessage) {
        httpStatus = aHttpStatus;
        message = aMessage;
    }

    /**
     * @return http status code
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param aHttpStatus http status code
     */
    public void setHttpStatus(HttpStatus aHttpStatus) {
        this.httpStatus = aHttpStatus;
    }

    /**
     * @return error message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * @param aMessage error aMessage
     */
    public void setMessage(String aMessage) {
        this.message = aMessage;
    }

}
