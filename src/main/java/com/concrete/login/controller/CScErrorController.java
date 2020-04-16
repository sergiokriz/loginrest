package com.concrete.login.controller;

import com.concrete.login.exception.response.CScApiError;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Description: API error
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Controller
public class CScErrorController implements ErrorController {

    private ErrorAttributes errorAttributes;

    /**
     * Contructs {@link CScErrorController}
     *
     * @param aErrorAttributes error attributes
     */
    public CScErrorController(ErrorAttributes aErrorAttributes) {
        errorAttributes = aErrorAttributes;
    }

    @RequestMapping(value = "/error")
    @ResponseBody
    public ResponseEntity<?> handleError(HttpServletRequest aRequest, WebRequest aWebRequest) {

        CScApiError apiError = resolveApiError(aRequest, aWebRequest);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    private CScApiError resolveApiError(HttpServletRequest aRequest, WebRequest aWebRequest) {

        String errorMessage;
        HttpStatus httpStatus = resolveHttpStatus(aRequest);

        if (HttpStatus.UNAUTHORIZED.equals(httpStatus)) {
            errorMessage = "Usuário e/ou senha inválidos.";
        } else if (HttpStatus.NOT_FOUND.equals(httpStatus)) {
            errorMessage = "Not found.";
        } else {
            Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(aWebRequest,
                    false);

            errorMessage = errorAttributesMap.entrySet()
                    .stream()
                    .filter(e -> e.getKey().equals("message"))
                    .map(e -> (String) e.getValue())
                    .findFirst().orElse("null");
        }

        CScApiError apiError = new CScApiError(errorMessage, httpStatus);
        return apiError;
    }

    private HttpStatus resolveHttpStatus(HttpServletRequest aRequest) {
        Integer statusCode = (Integer) aRequest
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            return HttpStatus.valueOf(statusCode);
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
