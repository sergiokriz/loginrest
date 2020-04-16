package com.concrete.login.config;

import com.concrete.login.security.CScSecurityConstants;
import com.concrete.login.security.CSiJwtTokenResolver;
import com.concrete.login.service.CSiCustomUserDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: Authorization filter
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScJWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final CSiJwtTokenResolver jwtTokenResolver;
    private final com.concrete.login.service.CSiCustomUserDetailService CSiCustomUserDetailService;

    public CScJWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                     CSiJwtTokenResolver aJwtTokenResolver,
                                     CSiCustomUserDetailService aCustomUserDetailService) {
        super(authenticationManager);
        this.jwtTokenResolver = aJwtTokenResolver;
        this.CSiCustomUserDetailService = aCustomUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest aRequest,
                                    HttpServletResponse aResponse,
                                    FilterChain aChain) throws IOException, ServletException {

        String header = aRequest.getHeader(CScSecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(CScSecurityConstants.TOKEN_PREFIX)) {
            aChain.doFilter(aRequest, aResponse);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(aRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        aChain.doFilter(aRequest, aResponse);
    }

    /**
     * Returns an user password authentication token from a given request
     *
     * @param aRequest http servlet request
     * @return Spring object
     */
    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest aRequest) {

        String token = aRequest.getHeader(CScSecurityConstants.HEADER_STRING);
        if (token == null) return null;

        String userEmail = jwtTokenResolver.getUserEmailFromJWT(token);
        UserDetails userDetails = CSiCustomUserDetailService.loadUserByUsername(userEmail);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = userEmail != null ?
                new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities()) : null;

        return usernamePasswordAuthenticationToken;
    }
}
