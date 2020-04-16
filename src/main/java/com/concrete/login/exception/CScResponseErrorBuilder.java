package com.concrete.login.exception;

import com.concrete.login.exception.response.CScApiError;
import com.concrete.login.exception.response.CSxHttpException;
import com.concrete.login.exception.response.CSxRequestAuthenticationException;
import com.concrete.login.exception.response.CSxRequestInternalErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

/**
 * Description: Response error builder
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScResponseErrorBuilder {

    int httpStatusCode;
    String jsonObject;

    public CScResponseErrorBuilder() {
    }

    /**
     * Returns the http status code of the error message
     *
     * @return http status code of the error
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Returns the json object to be user in the body of the message error
     *
     * @return json object of the body message error
     */
    public String getBodyJsonObject() {
        return jsonObject;
    }

    /**
     * Builds the json response object from the given exception
     *
     * @param aEx http inner exception
     * @return json object to be used in the body of the error message
     */
    public String buildResponseJson(CSxHttpException aEx) {

        try {

            CScApiError apiError = new CScApiError(aEx.getHttpStatus());
            apiError.setMessage(aEx.getMessage());
            httpStatusCode = apiError.getStatus().value();
            jsonObject = new ObjectMapper().writeValueAsString(apiError);

            return jsonObject;

        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Builds the json response object from the given exception
     *
     * @param aEx authentication exception used to create the json object of the returning error message
     */
    public void buildResponseJson(AuthenticationException aEx) {

        CSxRequestAuthenticationException requestAuthenticationException = new CSxRequestAuthenticationException();
        buildResponseJson(requestAuthenticationException);
    }

    /**
     * Builds the json response object from the inner given exception
     *
     * @param aEx inner system exception
     */
    public void buildResponseJson(Exception aEx) {

        CSxRequestInternalErrorException requestInternalErrorException = new CSxRequestInternalErrorException(aEx.getMessage());
        buildResponseJson(requestInternalErrorException);
    }

    /**
     * Returs the Api Error object to be used as Response Entity
     *
     * @param aEx inner http exception
     * @return returns the entity response normally used in the controller adviser
     */
    public ResponseEntity<CScApiError> buildResponseEntity(CSxHttpException aEx) {

        CScApiError apiError = new CScApiError(aEx.getHttpStatus());
        apiError.setMessage(aEx.getMessage());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
