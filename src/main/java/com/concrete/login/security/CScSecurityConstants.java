package com.concrete.login.security;

/**
 * Description: Security constants
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScSecurityConstants {

    public static final String SECRET = "DevDojoFoda";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/sign-up";
    public static final long EXPIRATION_TIME = 1800000L;

}

