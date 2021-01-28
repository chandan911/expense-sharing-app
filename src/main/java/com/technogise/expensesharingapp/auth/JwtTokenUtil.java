package com.technogise.expensesharingapp.auth;


import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.technogise.expensesharingapp.exceptions.AuthFailedException;
import com.technogise.expensesharingapp.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Component("JwtTokenUtil")
public class JwtTokenUtil implements Serializable {


    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long JWT_TOKEN_VALIDITY = 900_000;

    @Value("${jwt.secret}")
    private static String secret;

    private Key getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Object ExpiredJwtException;


    private Boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody().getExpiration();
        return expiration.before(new Date());
    }

    public String generateAuthTokenFor(User user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getId());
    }

    private String doGenerateToken(Map<String, Object> claims, Long subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject.toString()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey())
                .compact();
    }

    public Long validateToken(String token) throws AuthFailedException {
        try {
            String userIdFromToken = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();
            if (!isTokenExpired(token)) {
                return Long.parseLong(userIdFromToken);
            }
        } catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                IllegalArgumentException ex){
            LOGGER.error(ex.getMessage());
            throw new AuthFailedException();
        }
        return null;
    }
}

