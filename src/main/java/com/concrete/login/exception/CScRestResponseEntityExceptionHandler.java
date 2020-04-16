package com.concrete.login.exception;

import com.concrete.login.exception.response.CScApiError;
import com.concrete.login.exception.response.CSxHttpException;
import com.concrete.login.exception.response.CSxRequestInternalErrorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Description: Res response entity exception handler controller adviser
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@ControllerAdvice
public class CScRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Builds a response entity object for user not foun exception
     *
     * @param aEx exception
     * @return response entity api error object
     */
    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<CScApiError> handleUserNotFoundExceptions(UsernameNotFoundException aEx) {

        CScResponseErrorBuilder responseErrorBuilder = new CScResponseErrorBuilder();
        CSxRequestInternalErrorException internalErrorException = new CSxRequestInternalErrorException(aEx.getMessage());
        return responseErrorBuilder.buildResponseEntity(internalErrorException);
    }

    /**
     * Builds a response entity object from a "common" http exception
     *
     * @param aEx     exception used to build the response entity api error object
     * @param request the request that thrown the exception
     * @return response entity api error object
     */
    @ExceptionHandler(value = {CSxHttpException.class})
    protected ResponseEntity<CScApiError> handleHttpExceptions(CSxHttpException aEx, WebRequest request) {

        CScResponseErrorBuilder responseErrorBuilder = new CScResponseErrorBuilder();
        return responseErrorBuilder.buildResponseEntity(aEx);
    }

    /**
     * Generates response entity for all the other exceptions
     *
     * @param aEx     exception
     * @param body    object body
     * @param headers http header
     * @param status  http status code
     * @param request web request
     * @return response entity api error object
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception aEx,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {


        return new ResponseEntity<>(new CScApiError(aEx.getMessage(), status),
                headers,
                status);
    }
}
