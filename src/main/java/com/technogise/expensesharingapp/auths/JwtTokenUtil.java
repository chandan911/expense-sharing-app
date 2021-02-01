package com.technogise.expensesharingapp.auths;


import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.technogise.expensesharingapp.exceptions.AuthFailedException;
import com.technogise.expensesharingapp.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("JwtTokenUtil")
public class JwtTokenUtil implements Serializable {


    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long JWT_TOKEN_VALIDITY = 900_000;

    private static final String SECRET ="asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    private Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateAuthTokenFor(User user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getId());
    }

    public Long validateToken(String token) throws AuthFailedException {
        try {
                String userIdFromToken = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();
                if(!isTokenExpired(token)) return Long.parseLong(userIdFromToken);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage());
            throw new AuthFailedException();
        }
        return null;
    }

    private String doGenerateToken(Map<String, Object> claims, Long subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key)
                .compact();
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody().getExpiration();
        return expiration.before(new Date());
    }
}




