package com.concrete.login.config;

import com.concrete.login.exception.response.CScApiError;
import com.concrete.login.model.CScUserEO;
import com.concrete.login.service.CScUserFacade;
import com.concrete.login.to.signup.CScSignUpResponseTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: Authentication filter
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScJWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final CScUserFacade userFacade;

    public CScJWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                      CScUserFacade aUserFacade) {
        this.authenticationManager = authenticationManager;
        this.userFacade = aUserFacade;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest aRequest,
                                                HttpServletResponse aResponse) throws AuthenticationException {
        try {
            CScUserEO user = new ObjectMapper()
                    .readValue(aRequest.getInputStream(),
                            CScUserEO.class);

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()));

            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest aRequest,
                                            HttpServletResponse aResponse,
                                            FilterChain aChain,
                                            Authentication aAuthResult) throws IOException {

        String userEmail = resolveUserEmail(aAuthResult);

        if (userEmail != null) {

            CScSignUpResponseTO signUpResponseTO = userFacade.registerLogin(userEmail);
            writeJsonHttpServletResponse(aResponse,
                    HttpStatus.OK.value(),
                    signUpResponseTO);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest aRequest,
                                              HttpServletResponse aResponse,
                                              AuthenticationException aFailed) throws IOException, ServletException {

        writeJsonHttpServletResponse(aResponse,
                HttpStatus.UNAUTHORIZED.value(),
                new CScApiError("Usuário e/ou senha inválidos.",
                        HttpStatus.UNAUTHORIZED));
    }

    /**
     * Resolves the user email by a given Authentication
     *
     * @param aSpringAuthentication spring authentication
     * @return user email
     */
    private String resolveUserEmail(Authentication aSpringAuthentication) {

        if (aSpringAuthentication == null) {
            return null;
        }

        UserDetails userDetails = (User) aSpringAuthentication.getPrincipal();
        String userEmail = userDetails.getUsername();

        return userEmail;
    }

    /**
     * Writes to a httpServletResponse the result of a given object converted to JSon
     *
     * @param aServletResponse servlet response
     * @param aHttpStatusCode  http status code
     * @param aJsonObject      json object
     * @throws IOException expected exception
     */
    private void writeJsonHttpServletResponse(HttpServletResponse aServletResponse,
                                              int aHttpStatusCode,
                                              Object aJsonObject) throws IOException {

        aServletResponse.setStatus(aHttpStatusCode);
        aServletResponse.setContentType("application/json");
        aServletResponse.setCharacterEncoding("UTF-8");
        aServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(aJsonObject));
    }
}
