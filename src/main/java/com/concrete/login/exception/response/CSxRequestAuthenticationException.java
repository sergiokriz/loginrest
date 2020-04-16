package com.concrete.login.exception.response;

import org.springframework.http.HttpStatus;

/**
 * Description: Request authentication exception handler
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/


public class CSxRequestAuthenticationException extends CSxHttpException {

    /**
     * Constructs the CCxConflictException
     */
    public CSxRequestAuthenticationException() {
        super(HttpStatus.UNAUTHORIZED, "Usuário e/ou senha inválidos.");
    }

}
