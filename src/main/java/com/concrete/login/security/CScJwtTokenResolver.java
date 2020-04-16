package com.concrete.login.security;

import com.concrete.login.exception.response.CSxRequestTimeoutException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Description: JWT token resolver
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Service
public class CScJwtTokenResolver implements CSiJwtTokenResolver {

    @Override
    public String generateToken(Long aUserId) {

        Date now = new Date();
        Date expiringDateTime = new Date(now.getTime() + CScSecurityConstants.EXPIRATION_TIME);

        String jwt = Jwts.builder()
                .setSubject(Long.toString(aUserId))
                .setIssuedAt(new Date())
                .setExpiration(expiringDateTime)
                .signWith(SignatureAlgorithm.HS512, CScSecurityConstants.SECRET)
                .compact();

        return jwt;
    }

    @Override
    public String generateToken(String aUserName) {

        Date now = new Date();
        Date expiringDateTime = new Date(now.getTime() + CScSecurityConstants.EXPIRATION_TIME);

        String jwt = Jwts.builder()
                .setSubject(aUserName)
                .setIssuedAt(new Date())
                .setExpiration(expiringDateTime)
                .signWith(SignatureAlgorithm.HS512, CScSecurityConstants.SECRET)
                .compact();

        return jwt;
    }

    @Override
    public String generateTokenWithPrefix(String aUserName) {

        String jwt = CScSecurityConstants.TOKEN_PREFIX + generateToken(aUserName);
        return jwt;
    }

    @Override
    public Long getUserIdFromJWT(String aToken) {

        Claims claims = Jwts.parser()
                .setSigningKey(CScSecurityConstants.SECRET)
                .parseClaimsJws(aToken)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    @Override
    public String getUserEmailFromJWT(String aToken) {

        String username = Jwts.parser()
                .setSigningKey(CScSecurityConstants.SECRET)
                .parseClaimsJws(aToken.replace(CScSecurityConstants.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return username;
    }

    @Override
    public boolean validateToken(String aAuthToken) {

        try {
            Jwts.parser().setSigningKey(CScSecurityConstants.SECRET).parseClaimsJws(aAuthToken);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new CSxRequestTimeoutException("JWT token has expired.");
        }
    }

    @Override
    public String getJwtFromRequest(HttpServletRequest aHttpRequest) {

        String jwt = aHttpRequest.getHeader("Authorization");

        if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);
        }

        return null;
    }
}
